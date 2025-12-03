// User types
export interface User {
  userId: number
  loginId: string
  email: string
  nickname: string
  profileImageUrl?: string
  bio?: string
  isPrivate: boolean
  createdAt: string
}

// Transaction types
export interface Transaction {
  txId: number
  userId: number
  occurredAt: string
  amount: number
  txType: "EXPENSE" | "INCOME"
  categoryId: number
  category?: Category
  memo?: string
  paymentMethod?: string
  tags?: string[]
  createdAt: string
  updatedAt: string
}

export interface Category {
  categoryId: number
  name: string
  icon: string
  color: string
  userId?: number
}

// Challenge types
export interface Challenge {
  challengeId: number
  title: string
  description?: string
  budgetAmount: number
  startDate: string
  endDate: string
  entryFee?: number
  status: "ACTIVE" | "COMPLETED" | "CANCELLED"
  createdAt: string
  participantCount?: number
  userProgress?: ChallengeParticipant
}

export interface ChallengeParticipant {
  userId: number
  user?: User
  currentAmount: number
  joinedAt: string
  status: "ONGOING" | "SUCCESS" | "FAIL"
}

// Social types
export interface Post {
  postId: number
  userId: number
  user?: User
  boardId: number
  title: string
  content: string
  imageUrl?: string
  likeCount: number
  commentCount: number
  isLiked?: boolean
  createdAt: string
  updatedAt: string
}

export interface Comment {
  commentId: number
  postId: number
  userId: number
  user?: User
  content: string
  createdAt: string
}

// API Response types
export interface ApiResponse<T> {
  data: T
  message?: string
  status: number
}

export interface PaginatedResponse<T> {
  data: T[]
  total: number
  page: number
  size: number
  totalPages: number
}
