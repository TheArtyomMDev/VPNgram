package sds.guardvpn.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import sds.guardvpn.BuildConfig
import sds.guardvpn.common.Constants
import sds.guardvpn.common.MyVpnTunnel
import sds.guardvpn.data.remote.VpngramApi

val networkModule = module {

    fun provideRetrofit(): Retrofit {
        val interceptor2 = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG) interceptor2.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor2)
            .build()

        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()

        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
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

    single {
        MyVpnTunnel(get())
    }

}