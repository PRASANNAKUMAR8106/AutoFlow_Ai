import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

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
      body: Row(
        children: [
          if (isDesktop)
            NavigationRail(
              selectedIndex: _selectedIndex,
              onDestinationSelected: (int index) {
                setState(() {
                  _selectedIndex = index;
                });
              },
              labelType: NavigationRailLabelType.all,
              destinations: _sections.map((section) {
                return NavigationRailDestination(
                  icon: Icon(_getIconForSection(section)),
                  label: Text(section),
                );
              }).toList(),
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
      title: Text(_sections[_selectedIndex]),
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
      padding: const EdgeInsets.all(24.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            section,
            style: Theme.of(context).textTheme.headlineMedium?.copyWith(fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16),
          Expanded(
            child: Center(
              child: Text(
                'Foundation page for $section. Content will be implemented in subsequent phases.',
                style: const TextStyle(fontSize: 16, color: Colors.grey),
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
