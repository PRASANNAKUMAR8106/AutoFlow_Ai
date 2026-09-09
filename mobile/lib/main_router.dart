import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_bloc.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_state.dart';
import 'package:autoflow_ai/features/auth/presentation/pages/login_page.dart';
import 'package:autoflow_ai/shared/widgets/dashboard_shell.dart';

class AppRoot extends StatelessWidget {
  const AppRoot({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthBloc, AuthState>(
      builder: (context, state) {
        if (state is Authenticated) {
          return const DashboardShell(child: SizedBox());
        }
        return const LoginPage();
      },
    );
  }
}

class DashboardPage extends StatelessWidget {
  const DashboardPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('AutoFlow AI Dashboard')),
      body: const Center(child: Text('Welcome to AutoFlow AI')),
    );
  }
}
