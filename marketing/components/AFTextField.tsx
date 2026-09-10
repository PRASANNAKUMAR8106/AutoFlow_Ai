import React from 'react';
import { DesignTokens } from './DesignTokens';

interface AFTextFieldProps {
  label: string;
  value: string;
  onChange: (value: string) => void;
  type?: 'text' | 'password' | 'email';
  placeholder?: string;
  error?: string;
  disabled?: boolean;
  className?: string;
}

export const AFTextField: React.FC<AFTextFieldProps> = ({
  label,
  value,
  onChange,
  type = 'text',
  placeholder,
  error,
  disabled = false,
  className = '',
}) => {
  return (
    <div className={`flex flex-col gap-1 ${className}`}>
      <label className="text-sm font-medium text-slate-700">
        {label}
      </label>
      <input
        type={type}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={placeholder}
        disabled={disabled}
        className={`
          px-3 py-2 rounded-lg border transition duration-200 outline-none
          ${disabled ? 'bg-slate-50 text-slate-400 cursor-not-allowed' : 'bg-white text-slate-900'}
          ${error ? 'border-red-500 focus:border-red-600' : 'border-slate-200 focus:border-blue-500'}
          ${className}
        `}
      />
      {error && <span className="text-xs text-red-500">{error}</span>}
    </div>
  );
};
