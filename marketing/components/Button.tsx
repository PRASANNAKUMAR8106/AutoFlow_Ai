import React from 'react';
import { DesignTokens } from './DesignTokens';

interface AFButtonProps {
  text: string;
  onClick: () => void;
  primary?: boolean;
  isLoading?: boolean;
  className?: string;
}

export const AFButton: React.FC<AFButtonProps> = ({
  text,
  onClick,
  primary = true,
  isLoading = false,
  className = ''
}) => {
  const baseStyles = "px-6 py-3 rounded-lg font-semibold transition duration-200 flex items-center justify-center gap-2";
  const variantStyles = primary
    ? "bg-primary text-white hover:bg-blue-600"
    : "bg-slate-100 text-slate-900 hover:bg-slate-200";

  return (
    <button
      onClick={onClick}
      disabled={isLoading}
      className={`${baseStyles} ${variantStyles} ${className}`}
    >
      {isLoading && <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin" />}
      {text}
    </button>
  );
};
