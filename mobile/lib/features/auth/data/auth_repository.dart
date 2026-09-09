import 'package:autoflow_ai/core/api/api_client.dart';
import 'package:autoflow_ai/core/auth/token_storage.dart';

class AuthRepository {
  final ApiClient _apiClient;
  final TokenStorage _tokenStorage;

  AuthRepository(this._apiClient, this._tokenStorage);

  Future<void> register(String email, String password, String firstName, String lastName) async {
    final response = await _apiClient.post('/api/v1/auth/register', {
      'email': email,
      'password': password,
      'firstName': firstName,
      'lastName': lastName,
    });

    if (response.statusCode != 200) {
      throw Exception('Registration failed');
    }
  }

  Future<void> login(String email, String password) async {
    final response = await _apiClient.post('/api/v1/auth/login', {
      'email': email,
      'password': password,
    });

    if (response.statusCode == 200) {
      final data = response.data['data'];
      await _tokenStorage.saveAccessToken(data['accessToken']);
      await _tokenStorage.saveRefreshToken(data['refreshToken']);
    } else {
      throw Exception('Login failed');
    }
  }

  Future<void> logout() async {
    await _tokenStorage.clearTokens();
  }

  Future<bool> isAuthenticated() async {
    final token = await _tokenStorage.getAccessToken();
    return token != null;
  }
}
