package com.wearelupa.network;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.wearelupa.network.ApiResponse.Status.ERROR;
import static com.wearelupa.network.ApiResponse.Status.SUCCESS;
import static com.wearelupa.network.ApiResponse.Status.LOADING;

public class ApiResponse {

    /***********************************************************************************************
     *                                     ENUMS
     **********************************************************************************************/
    /** Track the status of the response */
    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }


    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    /** Status of the response */
    private final Status status;

    /** Data to be sent, if any */
    @Nullable
    private final Object data;

    /** Error of the response, if any */
    @Nullable
    private final Throwable error;


    /***********************************************************************************************
     *                                     CONSTRUCTOR
     **********************************************************************************************/
    private ApiResponse(Status status, @Nullable Object data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }


    /***********************************************************************************************
     *                                   STATIC METHODS
     **********************************************************************************************/
    public static ApiResponse loading(int completion) {
        return new ApiResponse(LOADING, completion, null);
    }

    public static ApiResponse success(@NonNull Object data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }


    /***********************************************************************************************
     *                                   PUBLIC METHODS
     **********************************************************************************************/
    public Status getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }
}
