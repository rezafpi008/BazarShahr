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

    @POST("api/job/register/")
    fun createJob(
        @Body requestBody: CreateJobRequest?,
    ): LiveData<GenericApiResponse<CreateJobResponse>>

    @POST("api/product/register/")
    fun createProduct(
        @Body requestBody: CreateProductRequest?,
    ): LiveData<GenericApiResponse<CreateProductResponse>>

    @POST("api/user/login/")
    fun signIn(
        @Body requestBody: SignInRequest?,
    ): LiveData<GenericApiResponse<SignInResponse>>

    @POST("api/user/register/")
    fun signUp(
        @Body requestBody: SignUpRequest?,
    ): LiveData<GenericApiResponse<SignUpResponse>>

    @POST("api/message/send/")
    fun sendMessage(
        @Body requestBody: SendMessageRequest?,
    ): LiveData<GenericApiResponse<MainResponse>>

    @POST("api/message/get/")
    fun getMessage(
        @Body requestBody: MessagesRequest?,
    ): LiveData<GenericApiResponse<MessagesResponse>>

    @POST("api/mall/categories/")
    fun getMallCategories(
        @Body requestBody: MallCategoriesRequest?,
    ): LiveData<GenericApiResponse<MallCategoriesResponse>>

    @POST("api/user/me/")
    fun getUserDetails(
        @Body requestBody: UserDetailsRequest?,
    ): LiveData<GenericApiResponse<UserDetailsResponse>>

    @POST("api/user/edit/")
    fun editUserDetails(
        @Body requestBody: EditUserRequest?,
    ): LiveData<GenericApiResponse<MainResponse>>

    @POST("api/ciritc/register/")
    fun sendSuggestions(
        @Body requestBody: SuggestionsRequest?,
    ): LiveData<GenericApiResponse<MainResponse>>

    @POST("api/cities/")
    fun getCities(): LiveData<GenericApiResponse<CitiesResponse>>
}