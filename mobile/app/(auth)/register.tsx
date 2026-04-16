import { Link, useRouter } from 'expo-router';
import React, { useState } from 'react';
import {
  ActivityIndicator,
  Pressable,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
import { Card } from '../../components/ui/card';
import { apiFetch } from '../../lib/api/client';
import { ApiPaths } from '../../lib/api/paths';
import { useSessionStore } from '../../stores/useSessionStore';
import { NexusColors, NexusRadius, NexusSpace } from '../../theme/nexus';

interface AuthEnvelope {
  data?: {
    accessToken?: string;
    userId?: string;
  };
}

/** US-001 — register via POST /auth/register */
export default function RegisterScreen() {
  const router = useRouter();
  const setSession = useSessionStore((s) => s.setSession);
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function onSubmit() {
    setError(null);
    setLoading(true);
    try {
      const json = await apiFetch<AuthEnvelope>(ApiPaths.auth.register, {
        method: 'POST',
        body: { fullName, email, password },
      });
      const token = json?.data?.accessToken ?? null;
      const uid = json?.data?.userId != null ? String(json.data.userId) : null;
      setSession(token, uid);
      router.replace('/(tabs)');
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Registration failed');
    } finally {
      setLoading(false);
    }
  }

  return (
    <View style={styles.screen}>
      <Text style={styles.title}>Register</Text>
      <Card>
        <Text style={styles.label}>Full name</Text>
        <TextInput
          value={fullName}
          onChangeText={setFullName}
          placeholder="Your name"
          placeholderTextColor={NexusColors.neutral500}
          style={styles.input}
        />
        <Text style={styles.label}>Email</Text>
        <TextInput
          value={email}
          onChangeText={setEmail}
          autoCapitalize="none"
          keyboardType="email-address"
          placeholder="you@example.com"
          placeholderTextColor={NexusColors.neutral500}
          style={styles.input}
        />
        <Text style={styles.label}>Password (min 8 characters)</Text>
        <TextInput
          value={password}
          onChangeText={setPassword}
          secureTextEntry
          placeholder="••••••••"
          placeholderTextColor={NexusColors.neutral500}
          style={styles.input}
        />
        {error ? <Text style={styles.error}>{error}</Text> : null}
        <Pressable
          onPress={onSubmit}
          disabled={loading}
          style={({ pressed }) => [
            styles.button,
            pressed && styles.buttonPressed,
            loading && styles.buttonDisabled,
          ]}
        >
          {loading ? (
            <ActivityIndicator color="#fff" />
          ) : (
            <Text style={styles.buttonLabel}>Create account</Text>
          )}
        </Pressable>
        <Link href="/(auth)/login" style={styles.link}>
          Already have an account? Sign in
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
  label: {
    fontSize: 12,
    fontWeight: '500',
    color: NexusColors.neutral700,
    marginBottom: NexusSpace.s1,
  },
  input: {
    borderWidth: 1,
    borderColor: NexusColors.neutral200,
    borderRadius: NexusRadius.md,
    paddingHorizontal: NexusSpace.s3,
    paddingVertical: NexusSpace.s3,
    marginBottom: NexusSpace.s3,
    color: NexusColors.neutral900,
    backgroundColor: NexusColors.background,
  },
  error: {
    color: NexusColors.danger,
    marginBottom: NexusSpace.s3,
    fontSize: 13,
  },
  button: {
    backgroundColor: NexusColors.brandPrimary,
    paddingVertical: NexusSpace.s3,
    borderRadius: NexusRadius.md,
    alignItems: 'center',
    marginBottom: NexusSpace.s4,
  },
  buttonPressed: { opacity: 0.9 },
  buttonDisabled: { opacity: 0.6 },
  buttonLabel: { color: '#fff', fontWeight: '600', fontSize: 16 },
  link: { color: NexusColors.brandPrimary, fontWeight: '600' },
});
