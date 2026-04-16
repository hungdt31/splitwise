import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React, { useState } from 'react';

interface AppProvidersProps {
  children: React.ReactNode;
}

/**
 * Bọc React Query — server state theo spec (Zustand cho local đã tách riêng).
 */
export function AppProviders({ children }: AppProvidersProps) {
  const [queryClient] = useState(
    () =>
      new QueryClient({
        defaultOptions: {
          queries: {
            staleTime: 60_000,
            retry: 1,
          },
        },
      })
  );

  return (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
  );
}
