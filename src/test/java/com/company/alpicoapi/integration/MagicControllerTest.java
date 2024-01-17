package com.company.alpicoapi.integration;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Execution(ExecutionMode.CONCURRENT)
public class MagicControllerTest {

    public final static String IMAGE = """
            <svg width="480" height="480" viewBox="0 0 480 480" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M261.91 126.6L262.506 126.596L262.937 126.184C265.439 123.791 267.1 120.823 267.1 117.4C267.1 113.204 264.588 109.567 260.866 107.046C257.136 104.518 252.059 103 246.5 103C240.988 103 235.936 104.52 232.219 107.047C228.511 109.568 226 113.205 226 117.4C226 120.9 227.643 124.113 230.557 126.467L230.974 126.803L231.51 126.8L261.91 126.6Z" fill="white" stroke="black" stroke-width="3"/>
            <path d="M316.915 126.6L317.646 126.593L318.09 126.013C320.565 122.785 322.1 118.871 322.1 114.6C322.1 109.325 319.723 103.926 315.973 100.177C312.205 96.4084 306.911 94.1883 301.136 95.6447C295.775 96.9725 290.783 99.0203 287.103 102.085C283.375 105.189 281 109.341 281 114.7C281 119.05 282.524 123.212 285.383 126.401L285.836 126.907L286.515 126.9L316.915 126.6Z" fill="white" stroke="black" stroke-width="3"/>
            <path d="M253 107.8C251.7 107.9 250.7 108.9 250.7 110.2C250.7 111.5 251.8 112.6 253.1 112.6C254.4 112.6 255.5 111.5 255.5 110.2C255.5 108.9 254.4 107.8 253.1 107.8H253L255.9 106.8C260.2 106.8 263.6 110.3 263.6 114.5C263.6 118.7 260.1 122.2 255.9 122.2C251.7 122.2 248.2 118.7 248.2 114.5C248.2 110.3 251.7 106.8 255.9 106.8" fill="black"/>
            <path d="M309.999 105.8C308.699 105.9 307.699 106.9 307.699 108.2C307.699 109.5 308.799 110.6 310.099 110.6C311.399 110.6 312.499 109.5 312.499 108.2C312.499 106.9 311.399 105.8 310.099 105.8H309.999L312.899 104.8C317.199 104.8 320.599 108.3 320.599 112.5C320.599 116.7 317.099 120.2 312.899 120.2C308.699 120.2 305.199 116.7 305.199 112.5C305.199 108.3 308.699 104.8 312.899 104.8" fill="black"/>
            <path d="M242.4 155.1H306.8C291.4 218.9 281.7 260.5 281.7 306.1C281.7 314.1 281.7 375.6 305.1 375.6C317.1 375.6 327.3 364.8 327.3 355.1C327.3 352.3 327.3 351.1 323.3 342.6C307.9 303.3 307.9 254.3 307.9 250.3C307.9 246.9 307.9 206.4 319.9 155.2H383.7C391.1 155.2 409.9 156.2 409.9 138C409.9 125.5 399.1 124.5 388.8 124.5H201.4C188.3 124.5 192.1 112.1 200.9 102.9C220.1 83.0001 238.8 104 241.8 101.6C245.5 98.6001 229.9 83.7001 221.6 80.6001C214.7 78.0001 195.6 85.1001 187.7 96.8001C170.2 122.7 182.6 155.2 196.8 155.2H229.3C216.8 197.9 202.5 247.5 155.8 347.2C151.2 356.3 151.2 357.5 151.2 360.9C151.2 372.9 161.5 375.7 166.6 375.7C183.1 375.7 187.7 360.9 194.5 337C203.6 307.9 203.6 306.8 209.3 284L242.4 155.1Z" fill="#0C7F99"/>
            <path d="M242.3 145.1C243.2 145.5 244 142.9 246.4 143.3C256.6 144.8 258.2 145.1 261.5 145L261.2 141.9C257.5 142 251.8 139.5 246.4 139.5C242.5 139.5 241.6 140.7 241.9 142.1" fill="black"/>
            </svg>
            """;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {
            "example", "please", "por-favor", "belekas123", "dziaugsmas",
            "čąčąčę", "zmoegelijukasa", "ą-čabas", "uhealth", "hila", "tenisas",
            "9~§21§", "`@;']"
    })
    public void whenSolvableString_thenReturnSvg(String magic) throws Exception {
        mockMvc.perform(get("/" + magic))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(content().string(IMAGE))
                .andExpect(header().exists(HttpHeaders.CONTENT_LENGTH));
    }

    @ParameterizedTest
    @ValueSource(strings = {"bitte", "onegaishimasu", "medikas", "_a12", "n1a2d3a4l5", "kompiūter8s", "8881", "!@#$%^&*()_+/.,\\][;[];a"})
    public void whenNotSolvableString_thenReturnError(String magic) throws Exception {
        mockMvc.perform(get("/" + magic))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("err.not.solvable ")));
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    public void whenEmptyString_thenBadRequest(String magic) throws Exception {
        mockMvc.perform(get("/" + magic))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("err.magic.invalidMagicString magic=")));
    }
}
