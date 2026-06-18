package org.example.departmentcrud;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DepartmentControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void shouldCreateDepartment() throws Exception {


        String request = """
                {
                  "departmentName":"IT",
                  "location":"Chennai"
                }
                """;


        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.departmentName")
                        .value("IT"))

                .andExpect(jsonPath("$.location")
                        .value("Chennai"));
    }





    @Test
    void shouldGetDepartmentById() throws Exception {


        String request = """
                {
                  "departmentName":"HR",
                  "location":"Bangalore"
                }
                """;


        String response =
                mockMvc.perform(post("/api/departments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))

                        .andExpect(status().isCreated())

                        .andReturn()
                        .getResponse()
                        .getContentAsString();



        JsonNode json = objectMapper.readTree(response);

        long id = json.get("id").asLong();



        mockMvc.perform(get("/api/departments/" + id))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id")
                        .value((int) id))

                .andExpect(jsonPath("$.departmentName")
                        .value("HR"));
    }





    @Test
    void shouldUpdateDepartment() throws Exception {


        String create = """
                {
                  "departmentName":"IT",
                  "location":"Chennai"
                }
                """;


        String response =
                mockMvc.perform(post("/api/departments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(create))

                        .andExpect(status().isCreated())

                        .andReturn()
                        .getResponse()
                        .getContentAsString();



        JsonNode json = objectMapper.readTree(response);

        long id = json.get("id").asLong();



        String update = """
                {
                  "departmentName":"Information Technology",
                  "location":"Hyderabad"
                }
                """;



        mockMvc.perform(
                        put("/api/departments/" + id)

                                .contentType(MediaType.APPLICATION_JSON)

                                .content(update)
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.departmentName")
                        .value("Information Technology"))

                .andExpect(jsonPath("$.location")
                        .value("Hyderabad"));
    }





    @Test
    void shouldDeleteDepartment() throws Exception {


        String request = """
                {
                  "departmentName":"Finance",
                  "location":"Mumbai"
                }
                """;



        String response =
                mockMvc.perform(post("/api/departments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))

                        .andExpect(status().isCreated())

                        .andReturn()
                        .getResponse()
                        .getContentAsString();



        JsonNode json = objectMapper.readTree(response);

        long id = json.get("id").asLong();



        mockMvc.perform(delete("/api/departments/" + id))

                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestWhenDepartmentNameAndLocationAreBlank() throws Exception {

        String request = """
            {
              "departmentName": "",
              "location": ""
            }
            """;

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.departmentName").exists())
                .andExpect(jsonPath("$.errors.location").exists());
    }

    @Test
    void shouldReturnBadRequestWhenDepartmentNameContainsInvalidCharacters() throws Exception {

        String request = """
            {
              "departmentName": "IT@@@",
              "location": "Chennai"
            }
            """;

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.departmentName").exists());
    }


    @Test
    void shouldReturnBadRequestWhenDepartmentIdIsNegative() throws Exception {

        mockMvc.perform(get("/api/departments/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Constraint violation"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.id").exists());
    }

    @Test
    void shouldReturnBadRequestWhenDepartmentIdIsNotNumber() throws Exception {

        mockMvc.perform(get("/api/departments/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid parameter type"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.id").exists());
    }

    @Test
    void shouldReturnNotFoundWhenDepartmentDoesNotExist() throws Exception {

        mockMvc.perform(get("/api/departments/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Department not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Department not found with id: 999"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateRequestIsInvalid() throws Exception {

        String request = """
            {
              "departmentName": "",
              "location": ""
            }
            """;

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.departmentName").exists())
                .andExpect(jsonPath("$.errors.location").exists());
    }
}