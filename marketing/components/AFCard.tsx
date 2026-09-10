import React from 'react';

interface AFCardProps {
  children: React.ReactNode;
  className?: string;
  padding?: 's' | 'm' | 'l';
}

export const AFCard: React.FC<AFCardProps> = ({
  children,
  className = '',
  padding = 'm',
}) => {
  const paddingMap = {
    s: 'p-2',
    m: 'p-4',
    l: 'p-6',
  };

  return (
    <div className={`
      bg-white rounded-xl border border-slate-200 shadow-sm
      ${paddingMap[padding]}
      ${className}
    `}>
      {children}
    </div>
  );
};
