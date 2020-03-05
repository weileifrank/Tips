public class MainActivity extends AppCompatActivity {

    private String baseUrl = "";//接口前缀

    private String imageUrl = "http://statics.juxia.com/uploadfile/content/2012/11/20121122101248333.jpg";
    private PhotoView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用glide加载网络图片
        mImageView = (PhotoView) findViewById(R.id.iv);
        Glide.with(this).load(imageUrl).into(mImageView);


        initRetrofit2();


    }

    private void initRetrofit2() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        Result result = retrofit.create(Result.class);
        Call<LogingBean> call = result.getLogin2("test", "test");
        call.enqueue(new Callback<LogingBean>() {
            @Override
            public void onResponse(Response<LogingBean> response, Retrofit retrofit) {
                LogingBean logingBean = response.body();
                Toast.makeText(MainActivity.this, logingBean.getUserInfo().getUserid(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void initRetrofit() {
        //使用retrofit获取接口数据
        //2.创建出retrofit对象进行访问网络

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
        Result result = retrofit.create(Result.class);
//        Call<ResponseBody> call = result.getHome();
//        Call<ResponseBody> call = result.getSearch();
//        Call<ResponseBody> call = result.getSearch(1, 10, "saleDown", "奶粉");
//        Call<ResponseBody> call = result.getSearch("search",1, 10, "saleDown", "奶粉");


        //page=0&pageNum=10&orderby=saleDown&keyword=奶粉

//        Map<String, String> datas = new HashMap<>();
//        datas.put("page","0");
//        datas.put("pageNum","10");
//        datas.put("orderby","saleDown");
//        datas.put("keyword","奶粉");
//        Call<ResponseBody> call = result.getSearch("search", datas);

//        Call<ResponseBody> call = result.getLogin("test", "test");
//        Call<ResponseBody> call = result.getLogin("login","test", "test");

//        Map<String, String> datas = new HashMap<>();
//        datas.put("username","test");
//        datas.put("password","test");
//        Call<ResponseBody> call = result.getLogin("login",datas);

//        Call<ResponseBody> call = result.getUserInfo();
        Call<ResponseBody> call = result.getUserInfo(20428);


//        call.execute();//同步请求，当前请求回调后，才执行下面的代码
//        call.enqueue();//异步请求组，重新开启子线程的进行网络请求耗时操作
        //异步请求，内存开启子线程进行耗时操作
        call.enqueue(new Callback<ResponseBody>() {
            //返回成功:服务器有响应
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                //当前已经执行在主线程中
                try {
                    String json = response.body().string();
//                    System.out.println(json);
                    Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //返回失败
            @Override
            public void onFailure(Throwable throwable) {

            }
        });
		
		
		
		 //批量获取微信用户信息
    public List<WxUserInfo> getUserListFromWx(Set<String> openIds) {
        List<WxUserInfo> wxUserInfos = null;
        String accessToken = getAccessToken();
        if (StringUtil.isEmpty(accessToken)) {
            return wxUserInfos;
        }
        List<Map<String, String>> requestList = new ArrayList<>();
        for (String openId : openIds) {
            HashMap<String, String> map = new HashMap<>();
            map.put("openid", openId);
            map.put("lang", "zh_CN");
            requestList.add(map);
        }

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("user_list", requestList);
        String rjson = new Gson().toJson(rmap);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(rjson, mediaType);
       Response<ResponseBody> response = getUserListFromWx(accessToken, body).execute();
        return wxUserInfos;
    }
    }


    //1.创建接口

    /**
     * 接口地址的拼接
     * baseUrl = "http://192.168.71.91:8080/market/" 当前是正确的接口拼接操作
     *
     * @GET("home")=>http://192.168.71.91:8080/market/home baseUrl = "http://192.168.71.91:8080/market/test"
     * @GET("home")=>http://192.168.71.91:8080/market/home baseUrl = "http://192.168.71.91:8080/market/test"
     * @GET("/home")=>http://192.168.71.91:8080/home
     */
    public interface Result {
        @GET("home")
        Call<ResponseBody> getHome();

        //在接口地址上直接写死参数
        @GET("search?page=0&pageNum=10&orderby=saleDown&keyword=奶粉")
        Call<ResponseBody> getSearch();

        //动态设置接口参数
        @GET("search")
        Call<ResponseBody> getSearch(@Query("page") int page, @Query("pageNum") int pageNum, @Query("orderby") String
                orderby, @Query("keyword") String keyword);

        //动态设置接口后缀
        @GET("{methodName}")
        Call<ResponseBody> getSearch(@Path("methodName") String methodName,@Query("page") int page, @Query("pageNum") int pageNum, @Query("orderby") String
                orderby, @Query("keyword") String keyword);

        //接口参数太多了，使用map集合的形式一次性传递数据
        @GET("{methodName}")
        Call<ResponseBody> getSearch(@Path("methodName") String methodName, @QueryMap Map<String,String> datas);

        //post请求登录接口
        @FormUrlEncoded//post请求必须加上encoding注意
        @POST("login")
        Call<ResponseBody> getLogin(@Field("username") String username,@Field("password") String password);

//        post动态设置接口后缀
        @FormUrlEncoded//post请求必须加上encoding注意
        @POST("{name}")
        Call<ResponseBody> getLogin(@Path("name") String name,@Field("username") String username,@Field("password") String password);

        //通过集合设置post请求参数
        @FormUrlEncoded//post请求必须加上encoding注意
        @POST("{name}")
        Call<ResponseBody> getLogin(@Path("name") String name, @FieldMap Map<String,String> datas);

        //账户中心  静态设置添加请求头
        @Headers("userid:20428")
        @GET("userinfo")
        Call<ResponseBody> getUserInfo();

        //账户中心  动态设置添加请求头
        @GET("userinfo")
        Call<ResponseBody> getUserInfo(@Header("userid") int userid);


        //post请求登录接口
        @FormUrlEncoded//post请求必须加上encoding注意
        @POST("login")
        Call<LogingBean> getLogin2(@Field("username") String username,@Field("password") String password);
		
		
		
		//批量获取微信用户信息  http请求方式: POST https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("cgi-bin/user/info/batchget")
    Call<ResponseBody> getUserListFromWx(@Query("access_token") String access_token,@Body RequestBody requestBody);
	
	
    }

}
