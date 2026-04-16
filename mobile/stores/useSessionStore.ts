import { create } from 'zustand';

/**
 * Minimal session store; persist via SecureStore in US-002.
 */
export interface SessionState {
  accessToken: string | null;
  userId: string | null;
  setSession: (accessToken: string | null, userId: string | null) => void;
  clearSession: () => void;
}

export const useSessionStore = create<SessionState>((set) => ({
  accessToken: null,
  userId: null,
  setSession: (accessToken, userId) => set({ accessToken, userId }),
  clearSession: () => set({ accessToken: null, userId: null }),
}));
