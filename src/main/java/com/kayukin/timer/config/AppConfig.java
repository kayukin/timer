package com.kayukin.timer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayukin.timer.service.DBusHandler;
import com.kayukin.timer.toggl.converter.StartRequestConverter;
import com.kayukin.timer.toggl.service.TogglClient;
import com.kayukin.timer.toggl.service.TogglService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.cinnamon.ScreenSaver;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.Credentials.basic;

@Configuration
public class AppConfig {
    private static final String API_TOGGL = "https://www.toggl.com/";
    private static final String AUTHORIZATION = "Authorization";
    private static final String API_TOKEN = "api_token";

    @Bean
    public DBusConnection dBusConnection(DBusSigHandler<ScreenSaver.ActiveChanged> handler) throws DBusException {
        DBusConnection connection = DBusConnection.getConnection(DBusConnection.DBusBusType.SESSION);
        connection.addSigHandler(ScreenSaver.ActiveChanged.class, handler);
        return connection;
    }

    @Bean
    public DBusSigHandler<ScreenSaver.ActiveChanged> handler(TogglService togglService) {
        return new DBusHandler(togglService);
    }

    @Bean
    public TogglService togglService(TogglClient togglClient, StartRequestConverter requestConverter) {
        return new TogglService(togglClient, requestConverter);
    }

    @Bean
    public StartRequestConverter requestConverter() {
        return new StartRequestConverter();
    }

    @Bean
    public TogglClient togglClient(ObjectMapper objectMapper,
                                   @Value("${toggl.api-key}") String apiKey) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader(AUTHORIZATION, basic(apiKey, API_TOKEN))
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(API_TOGGL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build()
                .create(TogglClient.class);
    }
}
