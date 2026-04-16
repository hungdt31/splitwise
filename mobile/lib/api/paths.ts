/** REST paths aligned with EventMate backend scaffold */

export const ApiPaths = {
  health: '/health',
  auth: {
    register: '/auth/register',
    login: '/auth/login',
    forgotPassword: '/auth/forgot-password',
  },
  users: {
    me: '/users/me',
    myEvents: '/users/me/events',
    balanceSummary: '/users/me/balance-summary',
    search: '/users/search',
    notificationPreferences: '/users/me/notification-preferences',
  },
  groups: {
    root: '/groups',
    invitations: (id: string) => `/groups/${id}/invitations`,
    members: (id: string) => `/groups/${id}/members`,
    member: (groupId: string, memberId: string) =>
      `/groups/${groupId}/members/${memberId}`,
    leave: (id: string) => `/groups/${id}/members/me`,
    byId: (id: string) => `/groups/${id}`,
  },
  events: {
    root: '/events',
    byId: (id: string) => `/events/${id}`,
    status: (id: string) => `/events/${id}/status`,
    participants: (id: string) => `/events/${id}/participants`,
    rsvpMe: (id: string) => `/events/${id}/participants/me`,
    expenses: (id: string) => `/events/${id}/expenses`,
    splitSummary: (id: string) => `/events/${id}/split-summary`,
    debtSummary: (id: string) => `/events/${id}/debt-summary`,
    payments: (id: string) => `/events/${id}/payments`,
    myBalance: (id: string) => `/events/${id}/balance/me`,
    activities: (id: string) => `/events/${id}/activities`,
    export: (id: string) => `/events/${id}/export`,
  },
  expenses: {
    byId: (id: string) => `/expenses/${id}`,
  },
  payments: {
    root: '/payments',
    confirm: (id: string) => `/payments/${id}/confirm`,
    reject: (id: string) => `/payments/${id}/reject`,
  },
  notifications: {
    unreadCount: '/notifications/unread-count',
  },
  analytics: {
    me: '/analytics/me',
    meByCategory: '/analytics/me/by-category',
    groupMembersSpending: (groupId: string) =>
      `/analytics/groups/${groupId}/members-spending`,
  },
} as const;
