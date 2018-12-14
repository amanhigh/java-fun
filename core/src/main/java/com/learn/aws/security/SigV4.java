package com.learn.aws.security;

import uk.co.lucasweb.aws.v4.signer.HttpRequest;
import uk.co.lucasweb.aws.v4.signer.Signer;
import uk.co.lucasweb.aws.v4.signer.credentials.AwsCredentials;
import uk.co.lucasweb.aws.v4.signer.hash.Sha256;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 2018-12-14 16:20.
 */
public class SigV4 {
    private static final String ACCESS_KEY = "PUBLIC_KEY";
    private static final String SECRET_KEY = "PRIVATE_KEY";

    public static void main(String[] args) throws Exception {
        String content="Hello !! Aman";
        String signature = getSignature(content);

        System.out.println(signature);
    }

    private static String getSignature(String content) throws NoSuchAlgorithmException, URISyntaxException {
        /* Digest content to Sha256 */
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String contentSha256 = String.valueOf(digest.digest(content.getBytes(StandardCharsets.UTF_8)));

        HttpRequest request = new HttpRequest("GET", new URI("https://examplebucket.s3.amazonaws.com?max-keys=2&prefix=J"));
        return Signer.builder()
                .awsCredentials(new AwsCredentials(ACCESS_KEY, SECRET_KEY))
                .header("Host", "examplebucket.s3.amazonaws.com")
                .header("x-amz-date", "20130524T000000Z")
                .header("x-amz-content-sha256", contentSha256)
                .buildS3(request, contentSha256)
                .getSignature();
    }
}
