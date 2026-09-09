import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_bloc.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_event.dart';
import 'package:autoflow_ai/features/auth/bloc/auth_state.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: BlocListener<AuthBloc, AuthState>(
          listener: (context, state) {
            if (state is Authenticated) {
              // Navigation to Dashboard would happen here
            } else if (state is AuthError) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text(state.message)),
              );
            }
          },
          child: Column(
            children: [
              TextField(
                decoration: const InputDecoration(labelText: 'Email'),
                onSubmitted: (val) {
                  // Set email in a controller or state
                },
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'Password'),
                obscureText: true,
                onSubmitted: (val) {
                  // Set password in a controller or state
                },
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  context.read<AuthBloc>().add(LoginRequested('test@example.com', 'Password123!'));
                },
                child: const Text('Login'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
