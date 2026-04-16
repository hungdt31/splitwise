import { Link } from 'expo-router';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Card } from '../../components/ui/card';
import { NexusColors, NexusSpace } from '../../theme/nexus';

/** US-020 — events dashboard (placeholder). */
export default function EventsHomeScreen() {
  return (
    <View style={styles.screen}>
      <Text style={styles.title}>EventMate</Text>
      <Text style={styles.subtitle}>Upcoming and ongoing events</Text>
      <Card>
        <Text style={styles.cardText}>
          Scaffold: carousel + list (GET /events?upcoming=true).
        </Text>
        <Link href="/(auth)/login" style={styles.link}>
          Sign in
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
    marginBottom: NexusSpace.s2,
  },
  subtitle: {
    fontSize: 14,
    color: NexusColors.neutral700,
    marginBottom: NexusSpace.s5,
  },
  cardText: {
    fontSize: 14,
    color: NexusColors.neutral700,
    marginBottom: NexusSpace.s4,
  },
  link: {
    color: NexusColors.brandPrimary,
    fontWeight: '600',
  },
});
