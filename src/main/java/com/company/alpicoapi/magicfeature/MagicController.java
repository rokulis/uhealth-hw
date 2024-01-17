package com.company.alpicoapi.magicfeature;

import com.company.alpicoapi.exceptions.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MagicController {
    private final MagicService magicService;

    public MagicController(MagicService magicService) {
        this.magicService = magicService;
    }

    @GetMapping("/{magic}")
    public ResponseEntity<byte[]> getMagicImage(@PathVariable String magic) {
        if (!StringUtils.hasText(magic)) {
            throw ApiException.bad("err.magic.invalidMagicString").addLabel("magic", magic);
        }
        byte[] image = magicService.getMagicImage(magic);
        return createResponse(image);
    }

    private static ResponseEntity<byte[]> createResponse(byte[] image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
