import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { AppProviders } from '../providers/AppProviders';
import { NexusColors } from '../theme/nexus';

export default function RootLayout() {
  return (
    <AppProviders>
      <StatusBar style="dark" />
      <Stack
        screenOptions={{
          headerStyle: { backgroundColor: NexusColors.neutral100 },
          headerTintColor: NexusColors.brandPrimaryDark,
          headerTitleStyle: { fontWeight: '600' },
        }}
      />
    </AppProviders>
  );
}
