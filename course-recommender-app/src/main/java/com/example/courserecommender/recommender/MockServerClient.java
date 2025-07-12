package com.example.courserecommender.recommender;

import com.example.generated.RecommendedCourseType;
import com.example.generated.RecommendedCoursesType;
import com.example.recommendercore.RecommendedCourse;
import com.example.courserecommender.mapper.CourseMapper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

@Component
public class MockServerClient {

    private static final String SOAP_ENDPOINT = "http://localhost:8088/mockRecommendedCoursesBinding";

    private static final String SOAP_REQUEST_BODY = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\"http://recommender.com/recommendations\">"
            +
            "<soapenv:Header/>" +
            "<soapenv:Body>" +
            "<rec:getRecommendedCourses/>" +
            "</soapenv:Body>" +
            "</soapenv:Envelope>";

    private final RestTemplate restTemplate = new RestTemplate();
    private final CourseMapper mapper;

    public MockServerClient(CourseMapper mapper) {
        this.mapper = mapper;
    }

    public List<RecommendedCourse> getRecommendations() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> request = new HttpEntity<>(SOAP_REQUEST_BODY, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(SOAP_ENDPOINT, request, String.class);
            String xml = response.getBody();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));

            NodeList nodeList = doc.getElementsByTagNameNS("*", "recommendedCourses");
            if (nodeList.getLength() == 0) {
                throw new RuntimeException("recommendedCourses element not found in SOAP response");
            }

            Node recommendedCoursesNode = nodeList.item(0);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(recommendedCoursesNode), new StreamResult(writer));
            String recommendedCoursesXml = writer.toString();

            JAXBContext jaxbContext = JAXBContext.newInstance(RecommendedCoursesType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            JAXBElement<RecommendedCoursesType> je = unmarshaller.unmarshal(
                    new StreamSource(new StringReader(recommendedCoursesXml)),
                    RecommendedCoursesType.class);

            RecommendedCoursesType root = je.getValue();

            List<RecommendedCourseType> generatedList = root.getRecommendedCourse();
            return mapper.fromGeneratedList(generatedList);

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
