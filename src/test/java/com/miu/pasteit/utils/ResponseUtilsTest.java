package com.miu.pasteit.utils;

import com.miu.pasteit.model.common.ErrorDetails;
import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.common.SuccessDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
@PrepareForTest(ResponseUtils.class)
class ResponseUtilsTest {

    @Test
    void buildSuccessRestResponse() {
        RestResponse<String> test = ResponseUtils.buildSuccessRestResponse(HttpStatus.OK, "Test");
        Assertions.assertThat(test.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(test.getSuccess()).isInstanceOf(SuccessDetails.class);
        Assertions.assertThat(test.getSuccess().getData()).isNotNull();
        Assertions.assertThat(test.getSuccess().getData()).isEqualTo("Test");
    }

    @Test
    void buildErrorRestResponse() {
        RestResponse<String> errorWithNoField = ResponseUtils.buildErrorRestResponse(HttpStatus.BAD_REQUEST, null, "Message No Field");
        RestResponse<String> errorWithField = ResponseUtils.buildErrorRestResponse(HttpStatus.BAD_REQUEST, "field", "Message Field");

        Assertions.assertThat(errorWithNoField).isNotNull();
        Assertions.assertThat(errorWithField).isNotNull();
        Assertions.assertThat(errorWithNoField.getError().size()).isEqualTo(1);
        Assertions.assertThat(errorWithField.getError().size()).isEqualTo(1);
        Assertions.assertThat(errorWithNoField.getError()).isInstanceOf(List.class);
        Assertions.assertThat(errorWithField.getError()).isInstanceOf(List.class);
        Assertions.assertThat(errorWithNoField.getError().get(0).getField()).isNull();
        Assertions.assertThat(errorWithField.getError().get(0).getField()).isNotNull();
        Assertions.assertThat(errorWithField.getError().get(0).getField()).isEqualTo("field");
        Assertions.assertThat(errorWithNoField.getError().get(0).getMessage()).isEqualTo("Message No Field");
        Assertions.assertThat(errorWithField.getError().get(0).getMessage()).isEqualTo("Message Field");
        Assertions.assertThat(errorWithNoField).isInstanceOf(RestResponse.class);
        Assertions.assertThat(errorWithField).isInstanceOf(RestResponse.class);
        Assertions.assertThat(errorWithNoField.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(errorWithField.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testBuildErrorRestResponse() {
        List<ErrorDetails> emptyErrorDetails = Collections.emptyList();
        RestResponse<ErrorDetails> error = ResponseUtils.buildErrorRestResponse(HttpStatus.INTERNAL_SERVER_ERROR, emptyErrorDetails);
        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getError()).isEmpty();
        Assertions.assertThat(error.getError().size()).isEqualTo(0);
        Assertions.assertThat(error.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void buildSuccessResponse() {
        ResponseEntity<RestResponse<String>> success = ResponseUtils.buildSuccessResponse(HttpStatus.OK, "success");
        Assertions.assertThat(success).isNotNull();
        Assertions.assertThat(success).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(success.getStatusCode()).isInstanceOf(HttpStatus.class);
        Assertions.assertThat(success.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(success.getBody()).isInstanceOf(RestResponse.class);
        Assertions.assertThat(success.getBody().getSuccess()).isInstanceOf(SuccessDetails.class);
        Assertions.assertThat(success.getBody().getError()).isNull();
        Assertions.assertThat(success.getBody().getSuccess().getData()).isNotNull();
        Assertions.assertThat(success.getBody().getSuccess().getData()).isEqualTo("success");
    }

    @Test
    void buildErrorResponse() {
        ResponseEntity<RestResponse<String>> error = ResponseUtils.buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "error", "error message");
        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(error.getStatusCode()).isInstanceOf(HttpStatus.class);
        Assertions.assertThat(error.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        Assertions.assertThat(error.getBody()).isInstanceOf(RestResponse.class);
        Assertions.assertThat(error.getBody().getError()).isInstanceOf(List.class);
        Assertions.assertThat(error.getBody().getError()).isNotEmpty();
        Assertions.assertThat(error.getBody().getError().get(0)).isInstanceOf(ErrorDetails.class);
        Assertions.assertThat(error.getBody().getError().get(0).getField()).isEqualTo("error");
        Assertions.assertThat(error.getBody().getError().get(0).getMessage()).isEqualTo("error message");
    }

    @Test
    @SuppressWarnings("unchecked")
    void createCustomResponse() throws Exception {
        HttpServletResponse mockResponse = new MockHttpServletResponse();
        RestResponse<ErrorDetails> mockRestResponse = ResponseUtils.buildErrorRestResponse(HttpStatus.BAD_REQUEST, "x", "y");
        //when(mockRestResponse.getStatus().value()).thenReturn(400);
        //doNothing().doThrow(IOException.class).when(ResponseUtils.class);
        ResponseUtils.createCustomResponse(mockResponse, mockRestResponse);
    }
}