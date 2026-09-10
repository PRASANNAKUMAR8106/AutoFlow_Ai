import React from 'react';

interface AFTypographyProps {
  variant: 'h1' | 'h2' | 'h3' | 'bodyLarge' | 'bodyMedium' | 'caption';
  children: React.ReactNode;
  className?: string;
  color?: 'primary' | 'secondary' | 'error';
}

export const AFTypography: React.FC<AFTypographyProps> = ({
  variant,
  children,
  className = '',
  color = 'primary',
}) => {
  const variantMap = {
    h1: 'text-4xl font-bold tracking-tight',
    h2: 'text-2xl font-semibold',
    h3: 'text-xl font-medium',
    bodyLarge: 'text-lg text-slate-600',
    bodyMedium: 'text-base text-slate-600',
    caption: 'text-sm text-slate-500',
  };

  const colorMap = {
    primary: 'text-slate-900',
    secondary: 'text-slate-500',
    error: 'text-red-600',
  };

  const Tag = {
    h1: 'h1',
    h2: 'h2',
    h3: 'h3',
    bodyLarge: 'p',
    bodyMedium: 'p',
    caption: 'span',
  }[variant] as any;

  return (
    <Tag className={`${variantMap[variant]} ${colorMap[color]} ${className}`}>
      {children}
    </Tag>
  );
};
