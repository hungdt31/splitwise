import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { Card } from '../../components/ui/card';
import { NexusColors, NexusSpace } from '../../theme/nexus';

/** EPIC 5 — thông báo (placeholder). */
export default function NotificationsScreen() {
  return (
    <View style={styles.screen}>
      <Text style={styles.title}>Thông báo</Text>
      <Card>
        <Text style={styles.body}>
          Khung: GET /notifications/unread-count + danh sách (FCM sau).
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
