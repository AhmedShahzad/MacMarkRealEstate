package com.example.rest;



import com.example.item.Property;
import com.example.response.AdvSearchRP;
import com.example.response.AppDetailRP;
import com.example.response.BraintreeCheckOutRP;
import com.example.response.CategoryRP;
import com.example.response.DetailRP;
import com.example.response.EditProfileRP;
import com.example.response.EditPropertyRP;
import com.example.response.FavoriteRP;
import com.example.response.HomeRP;
import com.example.response.LatestRP;
import com.example.response.LoginRP;
import com.example.response.PayUMoneyHashRP;
import com.example.response.PaymentSuccessRP;
import com.example.response.PaypalTokenRP;
import com.example.response.PlanRP;
import com.example.response.RazorPayTokenRP;
import com.example.response.RegisterRP;
import com.example.response.ReportRP;
import com.example.response.StripeCheckOutRP;
import com.example.response.SubscriptionRP;
import com.example.response.UploadPropertyRP;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //get home data
    @POST("home")
    @FormUrlEncoded
    Call<HomeRP> getHomeData(@Field("data") String data);

    //get latest data
    @POST("latest_property")
    @FormUrlEncoded
    Call<LatestRP> getLatestData(@Field("data") String data);

    //get cat data
    @POST("types")
    @FormUrlEncoded
    Call<CategoryRP> getCategoryData(@Field("data") String data, @Query("page") int page);

    //get app detail data
    @POST("app_details")
    @FormUrlEncoded
    Call<AppDetailRP> getAppDetailData(@Field("data") String data);

    //get all types data
    @POST("types_all")
    @FormUrlEncoded
    Call<CategoryRP> getAllCategoryData(@Field("data") String data);

    //get advance search data
    @POST("search_property")
    @FormUrlEncoded
    Call<AdvSearchRP> getAdvSearchData(@Field("data") String data, @Query("page") int page);


    //get advance search data
    @POST("normal_search_property")
    @FormUrlEncoded
    Call<AdvSearchRP> getHomeSearchData(@Field("data") String data, @Query("page") int page);

    //get popular data
    @POST("trending_property")
    @FormUrlEncoded
    Call<AdvSearchRP> getPopularData(@Field("data") String data);

    //get fav data
    @POST("user_favourite_post_list")
    @FormUrlEncoded
    Call<AdvSearchRP> getFavoriteData(@Field("data") String data, @Query("page") int page);

    //get cat list data
    @POST("property_by_types")
    @FormUrlEncoded
    Call<LatestRP> getCatByData(@Field("data") String data, @Query("page") int page);

    //get latest data
    @POST("property_details")
    @FormUrlEncoded
    Call<DetailRP> getDetailData(@Field("data") String data);

    //get  post view
    @POST("post_view")
    @FormUrlEncoded
    Call<JsonObject> getPostViewData(@Field("data") String data);

    //get  galley image delete
    @POST("user_property_gallery_delete")
    @FormUrlEncoded
    Call<JsonObject> getDeleteImageData(@Field("data") String data);

    //get  favorite
    @POST("post_favourite")
    @FormUrlEncoded
    Call<FavoriteRP> getFavUnFavData(@Field("data") String data);

    //get  login
    @POST("login")
    @FormUrlEncoded
    Call<LoginRP> getLoginData(@Field("data") String data);

    //get  socail login
    @POST("social_login")
    @FormUrlEncoded
    Call<LoginRP> getSocialLoginData(@Field("data") String data);

    //get  register
    @POST("signup")
    @FormUrlEncoded
    Call<RegisterRP> getRegisterData(@Field("data") String data);

    //get  forgot password
    @POST("forgot_password")
    @FormUrlEncoded
    Call<RegisterRP> getForgotData(@Field("data") String data);

    //get edit profile data
    @POST("profile_update")
    @Multipart
    Call<EditProfileRP> getEditProfileData(@Part("data") RequestBody data, @Part MultipartBody.Part part);

    //get add property data
    @POST("user_add_property")
    @Multipart
    Call<UploadPropertyRP> getUploadPropertyData(@Part("data") RequestBody data, @Part MultipartBody.Part featurePart, @Part MultipartBody.Part floorPart, @Part List<MultipartBody.Part> galleryPart);

    //get edit property data
    @POST("user_edit_property_save")
    @Multipart
    Call<UploadPropertyRP> getEditPropertyData(@Part("data") RequestBody data, @Part MultipartBody.Part featurePart, @Part MultipartBody.Part floorPart, @Part List<MultipartBody.Part> galleryPart);


    //get user property data
    @POST("user_property")
    @FormUrlEncoded
    Call<LatestRP> getUserPropertyData(@Field("data") String data);

    //get report data
    @POST("user_reports")
    @FormUrlEncoded
    Call<ReportRP> getReportPropertyData(@Field("data") String data);

    //get edit property data
    @POST("user_edit_property")
    @FormUrlEncoded
    Call<EditPropertyRP> getEditPropertyData(@Field("data") String data);

    //get subscription data
    @POST("check_user_plan")
    @FormUrlEncoded
    Call<SubscriptionRP> getSubscriptionData(@Field("data") String data);

    //get plan data
    @POST("subscription_plan")
    @FormUrlEncoded
    Call<PlanRP> getPlanData(@Field("data") String data);

    //get Payment Success data
    @POST("transaction_add")
    @FormUrlEncoded
    Call<PaymentSuccessRP> getPaymentSuccessData(@Field("data") String data);

    //get  payment method
    @POST("payment_settings")
    @FormUrlEncoded
    Call<ResponseBody> getPaymentMethodData(@Field("data") String data);

    //get  paypal token method
    @POST("get_braintree_token")
    @FormUrlEncoded
    Call<PaypalTokenRP> getPaypalTokenData(@Field("data") String data);

    //get  paypal checkout method
    @POST("braintree_checkout")
    @FormUrlEncoded
    Call<BraintreeCheckOutRP> getPaypalCheckOutData(@Field("data") String data);

    //get  pay u money hash method
    @POST("get_payu_hash")
    @FormUrlEncoded
    Call<PayUMoneyHashRP> getPayUMoneyHashData(@Field("data") String data);

    //get  razorpay token method
    @POST("razorpay_order_id_get")
    @FormUrlEncoded
    Call<RazorPayTokenRP> getRazorPayTokenData(@Field("data") String data);

    //get  stripe token method
    @POST("stripe_token_get")
    @FormUrlEncoded
    Call<StripeCheckOutRP> getStripeTokenData(@Field("data") String data);
}
