import React from 'react';
import { View } from 'react-native';
import { NexusColors, NexusRadius, NexusSpace } from '../../theme/nexus';

interface CardProps {
  children: React.ReactNode;
}

/** Nexus DS — surface / card (spec §1.2). */
export function Card({ children }: CardProps) {
  return (
    <View
      style={{
        borderRadius: NexusRadius.md,
        backgroundColor: NexusColors.background,
        padding: NexusSpace.s4,
        borderWidth: 1,
        borderColor: NexusColors.neutral200,
        shadowColor: '#000000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.08,
        shadowRadius: 4,
        elevation: 2,
      }}
    >
      {children}
    </View>
  );
}
