package io.github.vishnumad.nbascores.di

import android.util.Log
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.inject.assisted.dagger2.AssistedModule
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.github.vishnumad.nbascores.BuildConfig
import io.github.vishnumad.nbascores.remote.DataApi
import io.github.vishnumad.nbascores.remote.entities.PlayerStatline
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

const val ISO_FORMATTER = "iso_date_formatter"
const val LOCAL_ZONE = "local_zone"
const val DATE_ET = "current_date"

@AssistedModule
@Module(includes = [AssistedInject_AppModule::class]) // Generated module from AssistedInject
class AppModule {

    @Provides
    fun okhttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (BuildConfig.DEBUG)
                    Log.i("HttpLoggingInterceptor", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun retrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(DataApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun dataApi(retrofit: Retrofit): DataApi {
        return retrofit.create(DataApi::class.java)
    }

    @Provides
    fun moshi(): Moshi {
        return Moshi.Builder()
            .add(Wrapped.ADAPTER_FACTORY)
            .build()
    }

    @Provides
    @Named(ISO_FORMATTER)
    fun isoDateFormatter(): DateTimeFormatter {
        return DateTimeFormatter.BASIC_ISO_DATE
    }

    @Provides
    @Named(LOCAL_ZONE)
    fun systemZoneId(): ZoneId {
        return ZoneId.systemDefault()
    }

    @Provides
    @Named(DATE_ET)
    fun currentDateET(): LocalDate {
        val zone = ZoneId.of("America/New_York")
        return LocalDate.now(zone)
    }

    @Provides
    @Reusable
    fun playerStatlinesAdapter(moshi: Moshi): JsonAdapter<List<PlayerStatline>> {
        val type = Types.newParameterizedType(List::class.java, PlayerStatline::class.java)
        return moshi.adapter(type)
    }
}