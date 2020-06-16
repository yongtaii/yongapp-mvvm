package com.rnd.jworld.rxjavaretrofit.pojo

import com.google.gson.annotations.SerializedName

// Gson converter를 통해 Json을 파싱하여 해당 데이터 클래스에 매핑됨.
data class Currency(@SerializedName("cur_nm") val nation: String, @SerializedName("deal_bas_r") val rate: String)
