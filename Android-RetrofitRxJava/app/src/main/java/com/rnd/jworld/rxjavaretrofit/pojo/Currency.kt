package com.rnd.jworld.rxjavaretrofit.pojo

class Currency {
    @SerializedName("foo")
    @Expose
    var foo: String? = null
    @SerializedName("bar")
    @Expose
    var bar: String? = null
    @SerializedName("baz")
    @Expose
    var baz: String? = null
}


// example
//https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=3sSOPmu3LW1H5Ce6sbzWfrJPHSuD6oTZ&searchdate=20180102&data=AP01
//[
//{"result":1,"cur_unit":"AED","ttb":"288.78","tts":"294.61","deal_bas_r":"291.7","bkpr":"291","yy_efee_r":"0","ten_dd_efee_r":"0","kftc_bkpr":"291","kftc_deal_bas_r":"291.7","cur_nm":"아랍에미리트 디르함"},
//{"result":1,"cur_unit":"ATS","ttb":"0","tts":"0","deal_bas_r":"93.52","bkpr":"0","yy_efee_r":"0","ten_dd_efee_r":"0","kftc_bkpr":"0","kftc_deal_bas_r":"93.52","cur_nm":"오스트리아 실링"},
//{"result":1,"cur_unit":"AUD","ttb":"827.91","tts":"844.64","deal_bas_r":"836.28","bkpr":"836","yy_efee_r":"0","ten_dd_efee_r":"0","kftc_bkpr":"836","kftc_deal_bas_r":"836.28","cur_nm":"호주 달러"},
//{"result":1,"cur_unit":"BEF","ttb":"0","tts":"0","deal_bas_r":"31.9","bkpr":"0","yy_efee_r":"0","ten_dd_efee_r":"0","kftc_bkpr":"0","kftc_deal_bas_r":"31.9","cur_nm":"벨기에 프랑"},
//{"result":1,"cur_unit":"BHD","ttb":"2,811.48","tts":"2,868.27","deal_bas_r":"2,839.88","bkpr":"2,839","yy_efee_r":"0","ten_dd_efee_r":"0","kftc_bkpr":"2,839","kftc_deal_bas_r":"2,839.88","cur_nm":"바레인 디나르"}
//]