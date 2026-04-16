import { Stack } from 'expo-router';
import React from 'react';
import { NexusColors } from '../../theme/nexus';

export default function AuthLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: NexusColors.neutral100 },
        headerTintColor: NexusColors.brandPrimaryDark,
        title: 'Tài khoản',
      }}
    />
  );
}
