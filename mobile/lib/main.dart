import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:get_it/get_it.dart';
import 'package:autoflow_ai/core/api/api_client.dart';
import 'package:autoflow_ai/core/auth/token_storage.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_bloc.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_event.dart';
import 'package:autoflow_ai/features/auth/data/auth_repository.dart';
import 'package:autoflow_ai/main_router.dart';

final getIt = GetIt.instance;

void setupLocator() {
  getIt.registerLazySingleton<TokenStorage>(() => TokenStorage());
  getIt.registerLazySingleton<ApiClient>(() => ApiClient(getIt<TokenStorage>()));
  getIt.registerLazySingleton<AuthRepository>(() => AuthRepository(getIt<ApiClient>(), getIt<TokenStorage>()));
  getIt.registerFactory<AuthBloc>(() => AuthBloc(getIt<AuthRepository>()));
}

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  setupLocator();
  runApp(const AutoFlowApp());
}

class AutoFlowApp extends StatelessWidget {
  const AutoFlowApp({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => getIt<AuthBloc>()..add(AuthCheckRequested()),
      child: MaterialApp(
        title: 'AutoFlow AI',
        theme: ThemeData(
          primarySwatch: Colors.blue,
          useMaterial3: true,
        ),
        home: const AppRoot(),
      ),
    );
  }
}
