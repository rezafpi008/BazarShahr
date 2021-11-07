package com.bazar.bane.bazarshahr.api.main

import androidx.lifecycle.LiveData
import com.bazar.bane.bazarshahr.api.request.*
import com.bazar.bane.bazarshahr.api.response.*
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST("api/slider/all/")
    fun getSlider(
    ): LiveData<GenericApiResponse<SliderResponse>>

    @POST("auctions/published")
    fun getHome(
        @Query("page") page: Int
    ): LiveData<GenericApiResponse<HomeResponse>>

    @POST("api/search/")
    fun search(
        @Body requestBody: SearchRequest?
    ): LiveData<GenericApiResponse<SearchResponse>>

    @POST("api/jobs/categories/")
    fun getJobCategories(
        @Body requestBody: JobCategoryRequest?
    ): LiveData<GenericApiResponse<JobCategoriesResponse>>


    @POST("api/jobs/")
    fun getJobs(
        @Body requestBody: JobsRequest?,
    ): LiveData<GenericApiResponse<JobsResponse>>

    @POST("api/job/")
    fun getJobDetails(
        @Body requestBody: JobDetailsRequest?,
    ): LiveData<GenericApiResponse<JobDetailsResponse>>

    @POST("/api/products/")
    fun getProducts(
        @Body requestBody: ProductsRequest?,
    ): LiveData<GenericApiResponse<ProductsResponse>>

    @POST("/api/product/")
    fun getProductDetails(
        @Body requestBody: ProductDetailsRequest?,
    ): LiveData<GenericApiResponse<ProductDetailsResponse>>

    @POST("api/malls/")
    fun getMalls(
        @Body requestBody: MallsRequest?,
    ): LiveData<GenericApiResponse<MallsResponse>>

    @POST("api/mall/")
    fun getMallDetails(
        @Body requestBody: MallDetailsRequest?,
    ): LiveData<GenericApiResponse<MallDetailsResponse>>

    @POST("api/mall/")
    fun createJob(
        @Body requestBody: CreateJobRequest?,
    ): LiveData<GenericApiResponse<CreateJobResponse>>

    @POST("api/mall/")
    fun createProduct(
        @Body requestBody: CreateProductRequest?,
    ): LiveData<GenericApiResponse<CreateProductResponse>>

    @POST("api/mall/")
    fun signIn(
        @Body requestBody: SignInRequest?,
    ): LiveData<GenericApiResponse<SignInResponse>>

    @POST("api/mall/")
    fun signUp(
        @Body requestBody: SignUpRequest?,
    ): LiveData<GenericApiResponse<SignUpResponse>>
    /*@POST("auth")
    fun googleAccountRegister(
        @Body requestBody: SocialRegisterRequest?,
    ): LiveData<GenericApiResponse<TokenResponse>>

    @POST("auth")
    fun facebookAccountRegister(
        @Body requestBody: SocialRegisterRequest?,
    ): LiveData<GenericApiResponse<TokenResponse>>

    @POST("auth")
    fun phoneNumberRegister(
        @Body requestBody: PhoneRegisterRequest?
    ): LiveData<GenericApiResponse<PhoneLoginResponse>>

    @POST("auth/phone/code")
    fun phoneNumberResend(
        @Body requestBody: PhoneResendRequest?
    ): LiveData<GenericApiResponse<PhoneResendResponse>>

    @POST("auth/phone/verify")
    fun phoneNumberVerify(
        @Body requestBody: PhoneVerifyRequest?
    ): LiveData<GenericApiResponse<TokenResponse>>

    @POST("auth/email/verify")
    fun emailVerify(
        @Body requestBody: PhoneVerifyRequest?
    ): LiveData<GenericApiResponse<TokenResponse>>

    @POST("auth")
    fun emailRegister(
        @Body requestBody: EmailRequest?
    ): LiveData<GenericApiResponse<EmailLoginResponse>>

    @POST("auth/email/code")
    fun emailResend(
        @Body requestBody: EmailResendRequest?
    ): LiveData<GenericApiResponse<PhoneResendResponse>>

    @POST("referral")
    fun addReferralCode(
        @Header("token") token: String,
        @Body requestBody: AddReferralCodeRequest?,
    ): LiveData<GenericApiResponse<MainResponse>>

    @POST("auctions/bids")
    fun bidAuction(
        @Header("Authorization") token: String,
        @Body requestBody: BidAuctionRequest?
    ): LiveData<GenericApiResponse<BidAuctionResponse>>

    @GET("homepage")
    fun getExploreHeader(
        @Header("Authorization") token: String
    ): LiveData<GenericApiResponse<ExploreHeaderResponse>>

    @GET("auctions/published")
    fun getActiveAuction(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): LiveData<GenericApiResponse<AuctionItemResponse>>

    @GET("auctions/expired")
    fun getPastAuction(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): LiveData<GenericApiResponse<AuctionItemResponse>>

    @GET("myBags")
    fun getMyBag(
        @Header("Authorization") token: String,
        @Query("condition") condition: String,
        @Query("page") page: Int
    ): LiveData<GenericApiResponse<MyBagItemResponse>>

    @GET("auctions/{id}")
    fun getAuctionDetails(
        @Header("Authorization") token: String,
        @Path("id") auctionId: String
    ): LiveData<GenericApiResponse<AuctionDetailsResponse>>

    @GET("auctions/{id}/participants")
    fun getParticipantUsers(
        @Header("Authorization") token: String,
        @Path("id") auctionId: String,
        @Query("page") page: Int
    ): LiveData<GenericApiResponse<ParticipantsUserResponse>>

    @POST("actions")
    fun actionAuction(
        @Header("Authorization") token: String,
        @Body requestBody: ActionAuctionRequest?
    ): LiveData<GenericApiResponse<MainResponse>>

    @GET("actions/likes")
    fun getMyFavourites(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): LiveData<GenericApiResponse<MyFavouritesResponse>>

    @POST("payments/deposits")
    fun getReferenceId(
        @Header("Authorization") token: String,
        @Body requestBody: DepositAmountRequest?
    ): LiveData<GenericApiResponse<DepositAmountResponse>>

    @POST("payments/deposits/paypal/approvals")
    fun sendDepositResult(
        @Header("Authorization") token: String,
        @Body requestBody: DepositResultRequest?
    ): LiveData<GenericApiResponse<MainResponse>>*/
}