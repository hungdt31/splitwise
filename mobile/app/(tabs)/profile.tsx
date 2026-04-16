import { Link } from 'expo-router';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Card } from '../../components/ui/card';
import { NexusColors, NexusSpace } from '../../theme/nexus';

/** Profile tab (placeholder). */
export default function ProfileScreen() {
  return (
    <View style={styles.screen}>
      <Text style={styles.title}>Profile</Text>
      <Card>
        <Text style={styles.body}>
          Scaffold: PATCH /users/me, balance (US-007), notification prefs.
        </Text>
        <Link href="/(auth)/register" style={styles.link}>
          Create account
        </Link>
      </Card>
    </View>
  );
}

const styles = StyleSheet.create({
  screen: {
    flex: 1,
    padding: NexusSpace.s6,
    backgroundColor: NexusColors.neutral100,
  },
  title: {
    fontSize: 22,
    fontWeight: '700',
    color: NexusColors.neutral900,
    marginBottom: NexusSpace.s4,
  },
  body: {
    fontSize: 14,
    color: NexusColors.neutral700,
    marginBottom: NexusSpace.s4,
  },
  link: { color: NexusColors.brandPrimary, fontWeight: '600' },
});
