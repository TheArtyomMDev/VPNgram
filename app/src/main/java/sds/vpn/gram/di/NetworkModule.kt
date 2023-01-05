package sds.vpn.gram.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sds.vpn.gram.common.Constants
import sds.vpn.gram.data.remote.VpngramApi

val networkModule = module {

    fun provideRetrofit(): Retrofit {
        val interceptor2 = HttpLoggingInterceptor()

        interceptor2.level =
            HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor2)
            .build()


        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideAPIService(retrofit: Retrofit): VpngramApi {
        return retrofit.create(VpngramApi::class.java)
    }

    single {
        provideRetrofit()
    }

    single {
        provideAPIService(get())
    }

}