package com.example.safewo.api;

import com.example.safewo.model.Alert;
import com.example.safewo.model.Contact;
import com.example.safewo.model.SafePlaceDTO;
import com.example.safewo.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    // ===================== AUTH ENDPOINTS =====================

    @POST("/auth/register")
    Call<Map<String, Object>> register(@Body Map<String, String> request);

    @POST("/auth/login")
    Call<Map<String, Object>> login(@Body Map<String, String> request);

    // ===================== USER ENDPOINTS =====================

    @PUT("/api/users/{id}")
    Call<User> updateUser(@Path("id") Long id, @Body User user);

    // ===================== CONTACT ENDPOINTS =====================

    @POST("/api/contacts")
    Call<Contact> addContact(@Body Contact contact);

    @DELETE("/api/contacts/{id}")
    Call<Void> deleteContact(@Path("id") Long id);

    @GET("/api/contacts/user/{userId}")
    Call<List<Contact>> getContactsByUserId(@Path("userId") Long userId);

    // ===================== ALERT ENDPOINTS =====================

    @POST("/api/alerts/user/{userId}")
    Call<Alert> createAlert(@Path("userId") Long userId, @Body Map<String, Object> alertRequest);

    @GET("/api/alerts/user/{userId}")
    Call<List<Alert>> getAlertsByUserId(@Path("userId") Long userId);

    @GET("/api/alerts/{id}")
    Call<Alert> getAlertById(@Path("id") Long id);

    @PATCH("/api/alerts/{id}/status")
    Call<Alert> updateAlertStatus(@Path("id") Long id, @Body Map<String, String> statusUpdate);

    // ===================== SAFE PLACE ENDPOINTS =====================

    @POST("/api/safe-places")
    Call<Void> addSafePlace(@Body Map<String, Object> safePlace);

    @GET("/api/safe-places")
    Call<List<SafePlaceDTO>> getAllSafePlaces();

    // ===================== SOS ENDPOINTS =====================

    @Multipart
    @POST("/api/sos/send")
    Call<Void> sendSOS(
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part audioFile
    );

    @Multipart
    @POST("/api/sos/send/location")
    Call<Void> sendSOSLocation(
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude
    );
}
