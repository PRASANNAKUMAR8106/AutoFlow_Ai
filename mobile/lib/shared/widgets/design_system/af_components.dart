import 'package:flutter/material.dart';
import 'package:autoflow_ai/core/theme/app_colors.dart';
import 'package:autoflow_ai/core/theme/app_typography.dart';
import 'package:autoflow_ai/core/theme/app_dimensions.dart';

class AFButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final bool isPrimary;
  final bool isLoading;

  const AFButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.isPrimary = true,
    this.isLoading = false,
  });

  @override
  Widget build(BuildContext context) {
    return isLoading
        ? const Center(child: CircularProgressIndicator())
        : ElevatedButton(
            onPressed: onPressed,
            style: ElevatedButton.styleFrom(
              backgroundColor: isPrimary ? AppColors.primary : AppColors.secondaryLight,
              foregroundColor: isPrimary ? AppColors.textInverse : AppColors.textPrimary,
              textStyle: AppTypography.bodyLarge.copyWith(fontWeight: FontWeight.bold),
              padding: const EdgeInsets.symmetric(vertical: AppSpacing.m, horizontal: AppSpacing.l),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(AppRadius.s),
              ),
            ),
            child: Text(text),
          );
  }
}

class AFTextField extends StatelessWidget {
  final String label;
  final TextEditingController controller;
  final bool isPassword;
  final String? errorText;
  final TextInputType keyboardType;

  const AFTextField({
    super.key,
    required this.label,
    required this.controller,
    this.isPassword = false,
    this.errorText,
    this.keyboardType = TextInputType.text,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: AppTypography.labelSmall),
        const SizedBox(height: AppSpacing.xs),
        TextField(
          controller: controller,
          obscureText: isPassword,
          keyboardType: keyboardType,
          decoration: InputDecoration(
            hintText: 'Enter $label',
            errorText: errorText,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(AppRadius.s),
            ),
          ),
        ),
      ],
    );
  }
}

class AFCard extends StatelessWidget {
  final Widget child;
  final VoidCallback? onTap;

  const AFCard({super.key, required this.child, this.onTap});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 0,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppRadius.m),
        side: const BorderSide(color: AppColors.border),
      ),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(AppRadius.m),
        child: Padding(
          padding: const EdgeInsets.all(AppSpacing.m),
          child: child,
        ),
      ),
    );
  }
}
