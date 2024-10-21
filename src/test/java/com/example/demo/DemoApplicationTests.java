package com.example.demo;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

import io.qameta.allure.junitplatform.AllureJunitPlatform;
import org.junit.jupiter.api.extension.ExtendWith;
import com.example.demo.service.AuthenticationService;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.AccountResponse;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.repository.AccountRepository;
import com.example.demo.entity.Account;
import io.qameta.allure.*;
import io.qameta.allure.junitplatform.AllureJunitPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import com.example.demo.service.TokenService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@Epic("Authentication Service Tests")
@Feature("Login Tests")
class DemoApplicationTests {

	@InjectMocks
	private AuthenticationService authenticationService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private Authentication authentication;

	@Mock
	private TokenService tokenService;

	@BeforeEach
	void setUp() {
		// Any additional setup can be done here
		ModelMapper realModelMapper = new ModelMapper();
		when(modelMapper.map(any(Account.class), eq(AccountResponse.class)))
				.thenAnswer(invocation -> realModelMapper.map(invocation.getArgument(0), AccountResponse.class));
	}

	@Test
	@Story("Login with valid credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test successful login with correct phone number and password")
	@Step("Login test for success")
	void testLoginSuccess() {
		// Arrange
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPhone("0862826912");
		loginRequest.setPassword("password123");

		Account account = new Account();
		account.setPhone("0862826912");
		account.setPassword("password123");

		AccountResponse expectedResponse = new AccountResponse();
		expectedResponse.setPhone("0862826912");

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(account);
		when(modelMapper.map(account, AccountResponse.class)).thenReturn(expectedResponse);

		// Act
		AccountResponse result = authenticationService.login(loginRequest);

		// Debug
		System.out.println("Authentication result: " + authentication);
		System.out.println("Account from authentication: " + authentication.getPrincipal());
		System.out.println("Mapped response: " + expectedResponse);
		System.out.println("Actual result: " + result);

		// Assert
		assertNotNull(result);
		assertEquals("0862826912", result.getPhone());
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	@Story("Login with invalid credentials")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test failed login with incorrect phone number or password")
	@Step("Login test for failure")
	void testLoginFailure() {
		// Arrange
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPhone("1234567890");
		loginRequest.setPassword("wrongpassword");


		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new RuntimeException("Authentication failed"));

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> {
			authenticationService.login(loginRequest);
		});
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		System.out.println("Authentication result: " + authentication);
		System.out.println("Account from authentication: " + authentication.getPrincipal());
	}

	@Test
	void contextLoads() {
		// This test ensures that the Spring context loads correctly
		assertTrue(true);
	}

	@Test
	@Story("Login with empty credentials")
	@Severity(SeverityLevel.MINOR)
	@Description("Test login with empty phone number and password")
	@Step("Login test with empty credentials")
	void testLoginWithEmptyCredentials() {
		// Arrange
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPhone("");
		loginRequest.setPassword("");

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> {
			authenticationService.login(loginRequest);
		});
		verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	@Story("Login with null credentials")
	@Severity(SeverityLevel.MINOR)
	@Description("Test login with null phone number and password")
	@Step("Login test with null credentials")
	void testLoginWithNullCredentials() {
		// Arrange
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPhone(null);
		loginRequest.setPassword(null);

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> {
			authenticationService.login(loginRequest);
		});
		verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	@Story("Login with invalid phone format")
	@Severity(SeverityLevel.MINOR)
	@Description("Test login with invalid phone format")
	@Step("Login test with invalid phone format")
	void testLoginWithInvalidPhoneFormat() {
		// Arrange
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPhone("invalid-phone");
		loginRequest.setPassword("password123");

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> {
			authenticationService.login(loginRequest);
		});
		verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	@Story("Login with short password")
	@Severity(SeverityLevel.MINOR)
	@Description("Test login with short password")
	@Step("Login test with short password")
	void testLoginWithShortPassword() {
		// Arrange
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPhone("0862826912");
		loginRequest.setPassword("short");

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> {
			authenticationService.login(loginRequest);
		});
		verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	// Custom logging for debugging with Allure attachment
	@Attachment(value = "Authentication Debug", type = "text/plain")
	private String logAuthenticationResult(Authentication authentication, Account account, AccountResponse expectedResponse, AccountResponse result) {
		return "Authentication result: " + authentication + "\n" +
				"Account from authentication: " + account + "\n" +
				"Mapped response: " + expectedResponse + "\n" +
				"Actual result: " + result;
	}
}
