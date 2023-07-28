package com.example.pfe_project.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

class GlobalExceptionHandlerTest {
    /**
     * Method under test: {@link GlobalExceptionHandler#handleResourceNotFoundException(ResourceNotFoundException)}
     */
    @Test
    void testHandleResourceNotFoundException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Object> actualHandleResourceNotFoundExceptionResult = globalExceptionHandler
                .handleResourceNotFoundException(new ResourceNotFoundException("An error occurred"));
        assertTrue(actualHandleResourceNotFoundExceptionResult.hasBody());
        assertEquals(404, actualHandleResourceNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleResourceNotFoundExceptionResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleResourceNotFoundExceptionResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("An error occurred", errors.get(0));
        assertEquals(HttpStatus.NOT_FOUND, ((ApiError) actualHandleResourceNotFoundExceptionResult.getBody()).getStatus());
        assertEquals("Resource not found.",
                ((ApiError) actualHandleResourceNotFoundExceptionResult.getBody()).getMessage());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleResourceNotFoundException(ResourceNotFoundException)}
     */
    @Test
    void testHandleResourceNotFoundException2() {
        assertThrows(IllegalArgumentException.class,
                () -> (new GlobalExceptionHandler()).handleResourceNotFoundException(null));
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleResourceNotFoundException(ResourceNotFoundException)}
     */
    @Test
    void testHandleResourceNotFoundException3() {
        AnnotationConfigReactiveWebApplicationContext messageSource = new AnnotationConfigReactiveWebApplicationContext();
        messageSource.addApplicationListener(mock(ApplicationListener.class));

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        globalExceptionHandler.setMessageSource(messageSource);
        ResponseEntity<Object> actualHandleResourceNotFoundExceptionResult = globalExceptionHandler
                .handleResourceNotFoundException(new ResourceNotFoundException("An error occurred"));
        assertTrue(actualHandleResourceNotFoundExceptionResult.hasBody());
        assertEquals(404, actualHandleResourceNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandleResourceNotFoundExceptionResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleResourceNotFoundExceptionResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("An error occurred", errors.get(0));
        assertEquals(HttpStatus.NOT_FOUND,
                ((ApiError) actualHandleResourceNotFoundExceptionResult.getBody()).getStatus());
        assertEquals("Resource not found.",
                ((ApiError) actualHandleResourceNotFoundExceptionResult.getBody()).getMessage());
    }


    /**
     * Method under test: {@link GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((Executable) null,
                new BindException("Target", "Object Name"));

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleMethodArgumentNotValidResult = globalExceptionHandler
                .handleMethodArgumentNotValid(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
        assertEquals(400, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
        assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
        assertTrue(((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getErrors().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getStatus());
        assertEquals("Validation Errors", ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getMessage());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid2() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpHeaders headers = new HttpHeaders();
        assertThrows(IllegalArgumentException.class, () -> globalExceptionHandler.handleMethodArgumentNotValid(null,
                headers, null, new ServletWebRequest(new MockHttpServletRequest())));
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid3() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        BindingResult bindingResult = mock(BindingResult.class);
        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrorList);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((Executable) null, bindingResult);

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleMethodArgumentNotValidResult = globalExceptionHandler
                .handleMethodArgumentNotValid(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
        assertEquals(400, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
        assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
        assertEquals(fieldErrorList, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getErrors());
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getStatus());
        assertEquals("Validation Errors", ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getMessage());
        verify(bindingResult).getFieldErrors();
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid4() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Validation Errors", "Validation Errors", "Validation Errors"));
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrorList);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((Executable) null, bindingResult);

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleMethodArgumentNotValidResult = globalExceptionHandler
                .handleMethodArgumentNotValid(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
        assertEquals(400, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
        assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
        assertEquals(1, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getErrors().size());
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getStatus());
        assertEquals("Validation Errors", ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getMessage());
        verify(bindingResult).getFieldErrors();
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid5() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Validation Errors", "Validation Errors", "Validation Errors"));
        fieldErrorList.add(new FieldError("Validation Errors", "Validation Errors", "Validation Errors"));
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrorList);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((Executable) null, bindingResult);

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleMethodArgumentNotValidResult = globalExceptionHandler
                .handleMethodArgumentNotValid(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
        assertEquals(400, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
        assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
        assertEquals(2, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getErrors().size());
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getStatus());
        assertEquals("Validation Errors", ((ApiError) actualHandleMethodArgumentNotValidResult.getBody()).getMessage());
        verify(bindingResult).getFieldErrors();
    }



    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpMessageNotReadable() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMessageNotReadableResult = globalExceptionHandler
                .handleHttpMessageNotReadable(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
        assertEquals(400, actualHandleHttpMessageNotReadableResult.getStatusCodeValue());
        assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("https://example.org/example", errors.get(0));
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus());
        assertEquals("Malformed JSON found.",
                ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getMessage());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpMessageNotReadable2() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpHeaders headers = new HttpHeaders();
        assertThrows(IllegalArgumentException.class, () -> globalExceptionHandler.handleHttpMessageNotReadable(null,
                headers, null, new ServletWebRequest(new MockHttpServletRequest())));
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpMessageNotReadable(HttpMessageNotReadableException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpMessageNotReadable3() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMessageNotReadableResult = globalExceptionHandler
                .handleHttpMessageNotReadable(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleHttpMessageNotReadableResult.hasBody());
        assertEquals(400, actualHandleHttpMessageNotReadableResult.getStatusCodeValue());
        assertTrue(actualHandleHttpMessageNotReadableResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("Not all who wander are lost", errors.get(0));
        assertEquals(HttpStatus.BAD_REQUEST, ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getStatus());
        assertEquals("Malformed JSON found.",
                ((ApiError) actualHandleHttpMessageNotReadableResult.getBody()).getMessage());
        verify(ex).getMessage();
    }



    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotSupported() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotSupportedResult = globalExceptionHandler
                .handleHttpMediaTypeNotSupported(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleHttpMediaTypeNotSupportedResult.hasBody());
        assertEquals(400, actualHandleHttpMediaTypeNotSupportedResult.getStatusCodeValue());
        assertTrue(actualHandleHttpMediaTypeNotSupportedResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleHttpMediaTypeNotSupportedResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("https://example.org/example", errors.get(0));
        assertEquals(HttpStatus.BAD_REQUEST,
                ((ApiError) actualHandleHttpMediaTypeNotSupportedResult.getBody()).getStatus());
        assertEquals("Unsupported Media Type",
                ((ApiError) actualHandleHttpMediaTypeNotSupportedResult.getBody()).getMessage());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotSupported2() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpHeaders headers = new HttpHeaders();
        assertThrows(IllegalArgumentException.class, () -> globalExceptionHandler.handleHttpMediaTypeNotSupported(null,
                headers, null, new ServletWebRequest(new MockHttpServletRequest())));
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpMediaTypeNotSupported3() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpMediaTypeNotSupportedException ex = mock(HttpMediaTypeNotSupportedException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpMediaTypeNotSupportedResult = globalExceptionHandler
                .handleHttpMediaTypeNotSupported(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleHttpMediaTypeNotSupportedResult.hasBody());
        assertEquals(400, actualHandleHttpMediaTypeNotSupportedResult.getStatusCodeValue());
        assertTrue(actualHandleHttpMediaTypeNotSupportedResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleHttpMediaTypeNotSupportedResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("Not all who wander are lost", errors.get(0));
        assertEquals(HttpStatus.BAD_REQUEST,
                ((ApiError) actualHandleHttpMediaTypeNotSupportedResult.getBody()).getStatus());
        assertEquals("Unsupported Media Type",
                ((ApiError) actualHandleHttpMediaTypeNotSupportedResult.getBody()).getMessage());
        verify(ex).getMessage();
    }



    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpRequestMethodNotSupported() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException(
                "https://example.org/example");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpRequestMethodNotSupportedResult = globalExceptionHandler
                .handleHttpRequestMethodNotSupported(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleHttpRequestMethodNotSupportedResult.hasBody());
        assertEquals(404, actualHandleHttpRequestMethodNotSupportedResult.getStatusCodeValue());
        assertTrue(actualHandleHttpRequestMethodNotSupportedResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleHttpRequestMethodNotSupportedResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("Request method 'https://example.org/example' is not supported", errors.get(0));
        assertEquals(HttpStatus.NOT_FOUND,
                ((ApiError) actualHandleHttpRequestMethodNotSupportedResult.getBody()).getStatus());
        assertEquals("Method not supported",
                ((ApiError) actualHandleHttpRequestMethodNotSupportedResult.getBody()).getMessage());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpRequestMethodNotSupported2() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpHeaders headers = new HttpHeaders();
        assertThrows(IllegalArgumentException.class,
                () -> globalExceptionHandler.handleHttpRequestMethodNotSupported(null, headers, null,
                        new ServletWebRequest(new MockHttpServletRequest())));
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleHttpRequestMethodNotSupported3() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleHttpRequestMethodNotSupportedResult = globalExceptionHandler
                .handleHttpRequestMethodNotSupported(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualHandleHttpRequestMethodNotSupportedResult.hasBody());
        assertEquals(404, actualHandleHttpRequestMethodNotSupportedResult.getStatusCodeValue());
        assertTrue(actualHandleHttpRequestMethodNotSupportedResult.getHeaders().isEmpty());
        List errors = ((ApiError) actualHandleHttpRequestMethodNotSupportedResult.getBody()).getErrors();
        assertEquals(1, errors.size());
        assertEquals("Not all who wander are lost", errors.get(0));
        assertEquals(HttpStatus.NOT_FOUND,
                ((ApiError) actualHandleHttpRequestMethodNotSupportedResult.getBody()).getStatus());
        assertEquals("Method not supported",
                ((ApiError) actualHandleHttpRequestMethodNotSupportedResult.getBody()).getMessage());
        verify(ex).getMessage();
    }


}

