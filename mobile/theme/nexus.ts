/**
 * Nexus DS — design tokens (EventMate spec §1.1).
 * Dùng làm single source cho màu / spacing trong UI.
 */
export const NexusColors = {
  brandPrimary: '#4F46E5',
  brandPrimaryLight: '#E0E7FF',
  brandPrimaryDark: '#3730A3',
  success: '#10B981',
  warning: '#F59E0B',
  danger: '#EF4444',
  accent: '#06B6D4',
  neutral900: '#111827',
  neutral700: '#374151',
  neutral500: '#6B7280',
  neutral200: '#E5E7EB',
  neutral100: '#F3F4F6',
  background: '#FFFFFF',
} as const;

export const NexusSpace = {
  s1: 4,
  s2: 8,
  s3: 12,
  s4: 16,
  s5: 20,
  s6: 24,
} as const;

export const NexusRadius = {
  sm: 4,
  md: 8,
  lg: 16,
  full: 9999,
} as const;
