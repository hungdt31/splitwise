import { Tabs } from 'expo-router';
import React from 'react';
import { Text } from 'react-native';
import { NexusColors } from '../../theme/nexus';

function TabLabel({ label, focused }: { label: string; focused: boolean }) {
  return (
    <Text
      style={{
        fontSize: 11,
        fontWeight: focused ? '600' : '400',
        color: focused ? NexusColors.brandPrimary : NexusColors.neutral500,
      }}
    >
      {label}
    </Text>
  );
}

export default function TabsLayout() {
  return (
    <Tabs
      screenOptions={{
        headerShown: true,
        tabBarActiveTintColor: NexusColors.brandPrimary,
        tabBarInactiveTintColor: NexusColors.neutral500,
        tabBarStyle: {
          borderTopColor: NexusColors.neutral200,
          backgroundColor: NexusColors.background,
        },
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: 'Sự kiện',
          tabBarLabel: ({ focused }) => (
            <TabLabel label="Sự kiện" focused={focused} />
          ),
        }}
      />
      <Tabs.Screen
        name="groups"
        options={{
          title: 'Nhóm',
          tabBarLabel: ({ focused }) => (
            <TabLabel label="Nhóm" focused={focused} />
          ),
        }}
      />
      <Tabs.Screen
        name="notifications"
        options={{
          title: 'Thông báo',
          tabBarLabel: ({ focused }) => (
            <TabLabel label="Thông báo" focused={focused} />
          ),
        }}
      />
      <Tabs.Screen
        name="profile"
        options={{
          title: 'Cá nhân',
          tabBarLabel: ({ focused }) => (
            <TabLabel label="Cá nhân" focused={focused} />
          ),
        }}
      />
    </Tabs>
  );
}
