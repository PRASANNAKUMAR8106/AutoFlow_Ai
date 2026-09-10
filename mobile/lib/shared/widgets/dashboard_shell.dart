import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:autoflow_ai/core/theme/app_colors.dart';
import 'package:autoflow_ai/core/theme/app_typography.dart';
import 'package:autoflow_ai/core/theme/app_dimensions.dart';
import 'package:autoflow_ai/shared/widgets/design_system/af_components.dart';

class DashboardShell extends StatefulWidget {
  final Widget child;
  const DashboardShell({super.key, required this.child});

  @override
  State<DashboardShell> createState() => _DashboardShellState();
}

class _DashboardShellState extends State<DashboardShell> {
  int _selectedIndex = 0;

  final List<String> _sections = [
    'Home',
    'Workflows',
    'Contacts',
    'Inbox',
    'Analytics',
    'Billing',
    'Affiliate',
    'Settings',
  ];

  @override
  Widget build(BuildContext context) {
    final isDesktop = kIsWeb || MediaQuery.of(context).size.width > 900;

    return Scaffold(
      backgroundColor: AppColors.background,
      body: Row(
        children: [
          if (isDesktop)
            Container(
              width: 250,
              color: AppColors.surface,
              child: Column(
                children: [
                  const SizedBox(height: AppSpacing.xxxl),
                  const Padding(
                    padding: EdgeInsets.symmetric(horizontal: AppSpacing.l),
                    child: Text(
                      'AutoFlow AI',
                      style: AppTypography.h3,
                    ),
                  ),
                  const SizedBox(height: AppSpacing.xl),
                  Expanded(
                    child: ListView(
                      children: _sections.asMap().entries.map((entry) {
                        int idx = entry.key;
                        String section = entry.value;
                        bool isSelected = _selectedIndex == idx;
                        return ListTile(
                          leading: Icon(
                            _getIconForSection(section),
                            color: isSelected ? AppColors.primary : AppColors.textSecondary,
                          ),
                          title: Text(
                            section,
                            style: TextStyle(
                              color: isSelected ? AppColors.primary : AppColors.textSecondary,
                              fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
                            ),
                          ),
                          selected: isSelected,
                          onTap: () => setState(() => _selectedIndex = idx),
                        );
                      }).toList(),
                    ),
                  ),
                ],
              ),
            ),
          Expanded(
            child: Column(
              children: [
                if (!isDesktop)
                  _buildMobileHeader(),
                Expanded(
                  child: _buildCurrentSection(),
                ),
                if (!isDesktop)
                  _buildBottomNav(),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildMobileHeader() {
    return AppBar(
      backgroundColor: AppColors.surface,
      elevation: 0,
      title: Text(
        _sections[_selectedIndex],
        style: AppTypography.h3,
      ),
      actions: [
        IconButton(icon: const Icon(Icons.account_circle), onPressed: () {}),
      ],
    );
  }

  Widget _buildBottomNav() {
    return BottomNavigationBar(
      currentIndex: _selectedIndex,
      onTap: (index) => setState(() => _selectedIndex = index),
      type: BottomNavigationBarType.fixed,
      backgroundColor: AppColors.surface,
      selectedItemColor: AppColors.primary,
      unselectedItemColor: AppColors.textSecondary,
      items: _sections.take(5).map((section) {
        return BottomNavigationBarItem(
          icon: Icon(_getIconForSection(section)),
          label: section,
        );
      }).toList(),
    );
  }

  Widget _buildCurrentSection() {
    final section = _sections[_selectedIndex];
    return Padding(
      padding: const EdgeInsets.all(AppSpacing.l),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            section,
            style: AppTypography.h2,
          ),
          const SizedBox(height: AppSpacing.m),
          Expanded(
            child: AFCard(
              child: Center(
                child: Text(
                  'Foundation page for $section. Content will be implemented in subsequent phases.',
                  style: AppTypography.bodyLarge,
                  textAlign: TextAlign.center,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  IconData _getIconForSection(String section) {
    switch (section) {
      case 'Home': return Icons.dashboard;
      case 'Workflows': return Icons.account_tree;
      case 'Contacts': return Icons.people;
      case 'Inbox': return Icons.mail;
      case 'Analytics': return Icons.analytics;
      case 'Billing': return Icons.payment;
      case 'Affiliate': return Icons.card_giftcard;
      case 'Settings': return Icons.settings;
      default: return Icons.help_outline;
    }
  }
}
