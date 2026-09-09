import Link from 'next/link';

export default function HomePage() {
  return (
    <div className="flex flex-col items-center">
      {/* Hero Section */}
      <section className="px-6 py-20 text-center max-w-4xl mx-auto">
        <h1 className="text-5xl md:text-7xl font-extrabold tracking-tight mb-6">
          Automate Your <span className="text-primary">Social Presence</span> with AI
        </h1>
        <p className="text-xl text-slate-600 mb la-10 leading-relaxed">
          The all-in-one platform for creators and businesses to automate lead capture,
          content distribution, and audience engagement without losing the human touch.
        </p>
        <div className="flex gap-4 justify-center">
          <Link href="/dashboard" className="bg-primary text-white px-8 py-4 rounded-xl text-lg font-semibold hover:bg-blue-600 transition">
            Get Started Free
          </Link>
          <Link href="#features" className="border border-slate-300 px-8 py-4 rounded-xl text-lg font-semibold hover:bg-slate-50 transition">
            Explore Features
          </Link>
        </div>
      </section>

      {/* Features Section */}
      <section id="features" className="px-6 py-20 bg-slate-50 w-full">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-3xl font-bold text-center mb-12">Supercharge Your Growth</h2>
          <div className="grid md:grid-cols-3 gap-8">
            <FeatureCard
              title="AI Workflows"
              description="Build complex automation sequences that respond to your audience in real-time."
              icon="⚡"
            />
            <FeatureCard
              title="Lead Capture"
              description="Opt-in email collection that you control. No forced sign-ups for your content."
              icon="🎯"
            />
            <FeatureCard
              title="Multi-Channel"
              description="Manage your presence across all major social platforms from one dashboard."
              icon="🌐"
            />
          </div>
        </div>
      </section>

      {/* Pricing Section */}
      <section id="pricing" className="px-6 py-20 max-w-6xl mx-auto w-full">
        <h2 className="text-3xl font-bold text-center mb-12">Simple, Transparent Pricing</h2>
        <div className="grid md:grid-cols-2 gap-8 max-w-4xl mx-auto">
          <PricingCard
            name="Starter"
            price="Free"
            features={['3 Active Workflows', '500 Leads/mo', 'Basic Analytics']}
          />
          <PricingCard
            name="Pro"
            price="$49/mo"
            features={['Unlimited Workflows', 'Unlimited Leads', 'Advanced AI Tools', 'Priority Support']}
            highlighted={true}
          />
        </div>
      </section>
    </div>
  );
}

function FeatureCard({ title, description, icon }: { title: string, description: string, icon: string }) {
  return (
    <div className="p-8 bg-white rounded-2xl shadow-sm border border-slate-100 hover:shadow-md transition">
      <div className="text-4xl mb-4">{icon}</div>
      <h3 className="text-xl font-bold mb-2">{title}</h3>
      <p className="text-slate-600">{description}</p>
    </div>
  );
}

function PricingCard({ name, price, features, highlighted = false }: { name: string, price: string, features: string[], highlighted?: boolean }) {
  return (
    <div className={`p-8 rounded-2xl border ${highlighted ? 'border-primary ring-2 ring-primary/20 bg-blue-50' : 'border-slate-200 bg-white'}`}>
      <h3 className="text-2xl font-bold mb-2">{name}</h3>
      <div className="text-4xl font-extrabold mb-6">{price}</div>
      <ul className="space-y-4 mb-8">
        {features.map(f => (
          <li key={f} className="flex items-center gap-2">
            <span className="text-primary">✓</span> {f}
          </li>
        ))}
      </ul>
      <button className={`w-full py-3 rounded-lg font-semibold transition ${highlighted ? 'bg-primary text-white hover:bg-blue-600' : 'bg-slate-100 text-slate-900 hover:bg-slate-200'}`}>
        Get Started
      </button>
    </div>
  );
}
