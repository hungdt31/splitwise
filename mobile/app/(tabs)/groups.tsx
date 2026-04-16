import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Card } from '../../components/ui/card';
import { NexusColors, NexusSpace } from '../../theme/nexus';

/** EPIC 1 — danh sách nhóm (placeholder). */
export default function GroupsScreen() {
  return (
    <View style={styles.screen}>
      <Text style={styles.title}>Nhóm</Text>
      <Card>
        <Text style={styles.body}>
          Scaffold: SearchBar + chips — GET /groups
        </Text>
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
  body: { fontSize: 14, color: NexusColors.neutral700 },
});
