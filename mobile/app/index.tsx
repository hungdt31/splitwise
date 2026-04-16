import { Redirect } from 'expo-router';

/** Entry redirect — add auth guard later. */
export default function Index() {
  return <Redirect href="/(tabs)" />;
}
