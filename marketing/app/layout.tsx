import './globals.css';
import Link from 'next/link';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className="bg-white text-slate-900">
        <header className="flex items-center justify-between px-6 py-4 border-b border-slate-200">
          <div className="text-2xl font-bold text-primary">AutoFlow AI</div>
          <nav className="hidden md:flex gap-6">
            <Link href="/#features" className="text-slate-600 hover:text-primary">Features</Link>
            <Link href="/#pricing" className="text-slate-600 hover:text-primary">Pricing</Link>
            <Link href="/dashboard" className="bg-primary text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition">
              Go to Dashboard
            </Link>
          </nav>
        </header>
        <main>{children}</main>
        <footer className="border-t border-slate-200 py-8 text-center text-slate-500">
          <p>© 2026 AutoFlow AI. All rights reserved.</p>
        </footer>
      </body>
    </html>
  );
}
