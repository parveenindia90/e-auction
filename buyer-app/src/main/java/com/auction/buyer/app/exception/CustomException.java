package com.auction.buyer.app.exception;

/**
 * Exception handler for buyer component.
 */
public class CustomException extends RuntimeException{
    /**
     * error info identifier use to retrieve error info.
     */
    private final Integer errorCode;

    /**
     * Developer friendly message.
     */
    private final String developerMessage;

    /**
     * Error code passed by downstream systems.
     */
    private final String serviceErrorCode;

    /**
     * Instantiate exception with only {@link #errorCode}.
     *
     * @param errorCode error info identifier
     */
    public CustomException(final Integer errorCode) {
        this.errorCode = errorCode;
        this.developerMessage = null;
        this.serviceErrorCode = null;
    }

    /**
     * Instantiate exception with msp-omr {@link #errorCode} and developer friendly message {@link #developerMessage}.
     *
     * @param errorCode error info identifier
     * @param developerMessage developer friendly message
     */
    public CustomException(final Integer errorCode, final String developerMessage) {
        this.errorCode = errorCode;
        this.developerMessage = developerMessage;
        this.serviceErrorCode = null;
    }

    /**
     * Instantiate exception with only {@link #serviceErrorCode}.
     *
     * @param serviceErrorCode service error code
     */
    public CustomException(final String serviceErrorCode) {
        this.serviceErrorCode = serviceErrorCode;
        this.errorCode = null;
        this.developerMessage = null;
    }

    /**
     * Instantiate exception with {@link #serviceErrorCode} and developer friendly message {@link #developerMessage}.
     *
     * @param serviceErrorCode service error code
     * @param developerMessage developer friendly message
     */
    public CustomException(final String serviceErrorCode, final String developerMessage) {
        this.serviceErrorCode = serviceErrorCode;
        this.developerMessage = developerMessage;
        this.errorCode = null;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getServiceErrorCode() {
        return serviceErrorCode;
    }
}
