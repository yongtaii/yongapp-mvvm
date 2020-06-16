package com.rnd.jworld.rxjavaretrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rnd.jworld.rxjavaretrofit.pojo.Currency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Layoutmanager : 리사이클러뷰가 아이템을 화면에 표시할 때, 아이템 뷰들이 리아시클러뷰 내부에서 배치되는 형태를 관리하는 요소.
        // 리스트뷰 vs 리사이클러뷰 : 리스트뷰는 수직 스크롤만 가능하지만, 리사이클러뷰는 데이터목록을 수직,수평,Grid 등의 형태로 배치할 수 있다
        // recyclerview는 데이터목록을 수직,수평,Grid 등의 형태로 배치할 수 있는데, 이를 관리하는 요소가 Layout manager이다.
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter(ArrayList())
        recyclerView.adapter = adapter

        callEndPoint()

    }

    private fun callEndPoint(){
        // Retofit객체가 CurrencyApiClient.ApiService Interface를 create() 메서드를 통해 구현해준다.
        val currencyApiService = CurrencyApiClient.getCurrencyApiClient().create(CurrencyApiClient.ApiService::class.java)

        /**
         *  map vs flatmap in Collection
         *  val dataObject = listOf(Data(listOf("a","b","c"),Data(listOf("1","2","3"))
         *  val items:List<String> = dataObject.flatMap{it.items}  // [a,b,c,1,2,3] make it flat
         *  val items:List<String> = dataObject.map{it.items}  // []][a,b,c],[1,2,3]]
         *
         *  when use map vs faltmap
         *  map : 각 입력 값에 대해 하나의 출력.
         *  flatmap : 각 입력 값에 대해 0이상이 값 생산. -> 어짜피 내부적으로 나중에 merge를 한다.
         * */
        // getAllCurrencyInfo함수를 통해 만들어진 Observable 객체 (currencyApiService) 를 이용해 HTTP 요청을 한다
        val currencyObservable = currencyApiService.getAllCurrencyInfo(getString(R.string.api_key),"AP01")

        /**
         * Rxjava Threading은 스케쥴러의 도움으로 수행 됨.
         * Scheduler : 하나 이상의 스레드를 관리하는 스레드 풀
         * Scheduler가 작업 실행해야 할 때마다 풀에서 스레드를 가져와 해당 스레드에서 작업을 실행.
         */
        // subscribeOn : Observable이 처리해야할 연산자를 실행시킴.
        // observeOn : Observable이 옵저버에게 알림을 보낼때 상용할 스케쥴러.
        // Schedulers.computation() : 이벤트 루프에서 간단한 연산이나 콜백처리를 위해 쓰임. I/O 처리는 이걸로 쓰면 안된다.
        val disposable = currencyObservable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val newList = ArrayList<Currency>()
                for(currency in it){
                    newList.add(Currency("국가 : " + currency.nation,"환율 : " + currency.rate))
                }
                newList
            }
            .subscribe(
                { result ->  handleResults(result) },
                { error -> handleError(error)},
                { Log.d("TAG", "completed") }
            )
    }

    private fun handleResults(currencyList : ArrayList<Currency>){
        adapter.setData(currencyList)
    }

    private fun handleError(t: Throwable){
        Log.d("TAG", "handle Error\n" + t.printStackTrace())
        Toast.makeText(this,"Error Occrued !",Toast.LENGTH_LONG).show()
    }

}
