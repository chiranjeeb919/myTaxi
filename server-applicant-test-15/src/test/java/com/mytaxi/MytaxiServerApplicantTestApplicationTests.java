package com.mytaxi;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.datatransferobject.DriverSearchDTO;
import com.mytaxi.domainvalue.EngineType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MytaxiServerApplicantTestApplication.class)
@WebAppConfiguration
public class MytaxiServerApplicantTestApplicationTests
{

    private MediaType contentType =
        new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() throws Exception
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }


    @Test
    public void getDriverTest_With_ValidInput() throws Exception
    {
        this.mockMvc
            .perform(
                get("/v1/drivers/1"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.username", is("driver01")))
            .andExpect(jsonPath("$.password", is("driver01pw")));

    }


    @Test
    public void getDriverTest_With_InValidInput() throws Exception
    {
        this.mockMvc
            .perform(
                get("/v1/drivers/17"))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void createDriverTest() throws Exception
    {

        String driver09JSON =
            json(
                DriverDTO
                    .newBuilder()
                    .setCoordinate(null)
                    .setId(9l)
                    .setUsername("driver09")
                    .setPassword("driver09pw").createDriverDTO());

        this.mockMvc
            .perform(
                post("/v1/drivers")
                    .contentType(contentType)
                    .content(driver09JSON))
            .andExpect(status().isCreated());

    }


    @Test
    public void createDriverTestWithNullUserName() throws Exception
    {

        String driver09JSON =
            json(
                DriverDTO
                    .newBuilder()
                    .setCoordinate(null)
                    .setId(9l)
                    .setPassword("driver09pw").createDriverDTO());

        this.mockMvc
            .perform(
                post("/v1/drivers")
                    .contentType(contentType)
                    .content(driver09JSON))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void createDriverTestWithNullPassword() throws Exception
    {

        String driver09JSON =
            json(
                DriverDTO
                    .newBuilder()
                    .setCoordinate(null)
                    .setId(9l)
                    .setPassword("driver09pw").createDriverDTO());

        this.mockMvc
            .perform(
                post("/v1/drivers")
                    .contentType(contentType)
                    .content(driver09JSON))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void createDriverTestWithNullUsernameAndPassword() throws Exception
    {

        String driver09JSON =
            json(
                DriverDTO
                    .newBuilder()
                    .setCoordinate(null)
                    .setId(9l)
                    .setPassword("driver09pw").createDriverDTO());

        this.mockMvc
            .perform(
                post("/v1/drivers")
                    .contentType(contentType)
                    .content(driver09JSON))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void deleteDriverTest() throws Exception
    {

        this.mockMvc
            .perform(delete("/v1/drivers/1"))
            .andExpect(status().is2xxSuccessful());

    }


    @Test
    public void deleteDriverTestWithError() throws Exception
    {

        this.mockMvc
            .perform(delete("/v1/drivers/10"))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void updateLocationTest() throws Exception
    {
        this.mockMvc
            .perform(
                put("/v1/drivers/1")
                    .param("longitude", "170.0")
                    .param("latitude", "90.0"))
            .andExpect(status().is2xxSuccessful());

        this.mockMvc
            .perform(
                get("/v1/drivers/1"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.username", is("driver01")))
            .andExpect(jsonPath("$.coordinate.latitude", is(90.0)));

    }


    @Test(expected = Exception.class)
    public void updateLocationWithWrongCoodinateTest() throws Exception
    {
        this.mockMvc
            .perform(
                put("/v1/drivers/1")
                    .param("longitude", "181.0")
                    .param("latitude", "90.0"))
            .andExpect(status().is5xxServerError());
    }


    @Test
    public void selectCarTest() throws Exception
    {

        this.mockMvc
            .perform(
                put("/v1/drivers/1/car/1"))
            .andExpect(status().is2xxSuccessful());

        this.mockMvc
            .perform(
                get("/v1/drivers/1"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.carDTO.licensePlate", is("FRW-J274")));

    }


    @Test
    public void selectCarWithWrongCarIdTest() throws Exception
    {

        this.mockMvc
            .perform(
                put("/v1/drivers/1/car/3")
                    .contentType(contentType))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void findDriversTest() throws Exception
    {
        this.mockMvc
            .perform(
                put("/v1/drivers/1/car/2")
                    .contentType(contentType))
            .andExpect(status().is2xxSuccessful());

        String searchCriteria =
            json(
                DriverSearchDTO
                    .newBuilder()
                    .setUsername("driver01")
                    .setLicensePlate("SADGH18")
                    .setRating(4.5f)
                    .createDriverDTO());

        this.mockMvc
            .perform(
                post("/v1/drivers/search")
                    .contentType(contentType)
                    .content(searchCriteria))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$[0].carDTO.licensePlate", is("SADGH18")));
    }


    @Test
    public void findDriversWithInvalidDetailsTest() throws Exception
    {
        this.mockMvc
            .perform(
                put("/v1/drivers/2/car/3")
                    .contentType(contentType))
            .andExpect(status().is2xxSuccessful());

        String searchCriteria =
            json(
                DriverSearchDTO
                    .newBuilder()
                    .setUsername("driver02")
                    .setLicensePlate("SADGH19")
                    .setRating(4.4f)
                    .createDriverDTO());

        this.mockMvc
            .perform(
                post("/v1/drivers/search")
                    .contentType(contentType)
                    .content(searchCriteria))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$", empty()));
    }


    @Test
    public void getCarTest_With_ValidInput() throws Exception
    {
        this.mockMvc
            .perform(
                get("/v1/cars/1"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.licensePlate", is("FRW-J274")));

    }


    @Test
    public void getCarTest_With_InValidInput() throws Exception
    {
        this.mockMvc
            .perform(
                get("/v1/cars/6"))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void createCarTest() throws Exception
    {

        String carDHFH67Json =
            json(
                CarDTO
                    .newBuilder()
                    .setConvertible(true)
                    .setEngineType(EngineType.GAS)
                    .setLicensePlate("DHFH")
                    .setManufacturer(1l)
                    .setRating(5.3f)
                    .setSeatCount(4)
                    .createCarDTO());

        this.mockMvc
            .perform(
                post("/v1/cars/")
                    .contentType(contentType)
                    .content(carDHFH67Json))
            .andExpect(status().isCreated());

    }


    @Test
    public void createCarWithNullLicensePlateTest() throws Exception
    {

        String carDHFH67Json =
            json(
                CarDTO
                    .newBuilder()
                    .setConvertible(true)
                    .setEngineType(EngineType.GAS)
                    .setManufacturer(1l)
                    .setRating(5.3f)
                    .setSeatCount(4)
                    .createCarDTO());

        this.mockMvc
            .perform(
                post("/v1/cars/")
                    .contentType(contentType)
                    .content(carDHFH67Json))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void deleteCarTest() throws Exception
    {

        this.mockMvc
            .perform(delete("/v1/cars/1"))
            .andExpect(status().is2xxSuccessful());

    }


    @Test
    public void deleteCarTestWithError() throws Exception
    {

        this.mockMvc
            .perform(delete("/v1/cars/10"))
            .andExpect(status().is4xxClientError());

    }


    @Test
    public void updateCarTest() throws Exception
    {
        String carDHFH67Json =
            json(
                CarDTO
                    .newBuilder()
                    .setConvertible(true)
                    .setLicensePlate("SADGH194")
                    .setEngineType(EngineType.PETROL)
                    .setManufacturer(1l)
                    .setRating(5.3f)
                    .setSeatCount(4)
                    .createCarDTO());

        this.mockMvc
            .perform(
                put("/v1/cars/3")
                    .contentType(contentType)
                    .content(carDHFH67Json))
            .andExpect(status().is2xxSuccessful());

        this.mockMvc
            .perform(
                get("/v1/cars/3"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.licensePlate", is("SADGH194")));

    }


    protected String json(Object o) throws IOException
    {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
            o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
