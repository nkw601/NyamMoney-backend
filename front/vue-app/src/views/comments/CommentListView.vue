<template>
  <div class="mt-8">
    <h2 class="font-semibold mb-4">
      댓글 {{ totalElements }}개  ·  {{ page + 1 }} / {{ totalPages }} 페이지
    </h2>

    <p v-if="loading">댓글을 불러오는 중...</p>
    <p v-else-if="comments.length===0">아직 댓글이 없습니다.</p>

    <ul v-else>
      <li
        v-for="comment in comments"
        :key="comment.commentId"
        class="mb-3 p-3 rounded bg-gray-50"
      >
      
      <!-- 수정 화면 -->
        <div v-if="editingCommentId === comment.commentId">
          <textarea
            v-model="editingContent" rows="2" class="w-full p-2 border rounded" />
          <div class="mt-2 text-right">
            <button @click="saveEdit(comment.commentId)">저장 |</button>
            <button @click="cancelEdit"> 취소</button>
          </div>
        </div>
      <!-- 일반 화면 -->
        <div v-else>
          <p class="text-sm font-medium">
            {{ comment.author?.nickname || '익명' }}
          </p>
          <p class="text-sm">{{ comment.content }}</p>
          <!-- 권한 확인해서 수정,삭제 버튼 조회하도록 -->
          <button v-if="isMyComment(comment) && editingCommentIf !== comment.commentId"
          class="text-xs text-gray-400" @click="startEdit(comment)">수정 | </button>
          <button v-if="isMyComment(comment) && editingCommentIf !== comment.commentId"
          class="text-xs text-gray-400" @click="deleteComment(comment.commentId)"> 삭제</button>
          <p class="text-xs text-gray-400">
            {{ comment.createdAt }}
          </p>
        </div>
      </li>
    </ul>

    <!-- 페이지네이션 -->
    <div
      v-if="totalPages > 1"
      class="flex justify-center gap-2 mt-6">
      <button v-for="p in totalPages" :key="p"
        @click="goPage(p - 1)"
        :disabled="loading"
        class="px-3 py-1 border rounded"
        :class="{
          'bg-orange-500 text-white': page === p - 1,
        }"
      >
        {{ p }}
      </button>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useCommentStore } from '@/stores/comment.store'
import { useAuthStore } from '../../stores/auth';

export default {
  props: {
    boardId: {
      type: [Number, String],
      required: true,
    },
    postId: {
      type: [Number, String],
      required: true,
    },
  },

  setup(props) {
    const authStore = useAuthStore()
    const commentStore = useCommentStore()
    const { comments, loading, page, totalPages, totalElements } = storeToRefs(commentStore)
    
    // 수정 중인 댓글 누구? 뭐?
    const editingCommentId = ref(null)
    const editingContent = ref('')
    // 권한 확인하기
    const isMyComment = (comment)=>{
      return comment.author.userId === authStore.userId
    }

    onMounted(() => {
      commentStore.loadComments(props.boardId, props.postId)
      console.log('CommentList props', props.boardId, props.postId)
    })

    const goPage = (p)=>{
      commentStore.loadComments(props.boardId, props.postId, p)
    }

    // 수정 버튼 클릭
    const startEdit = (comment)=>{
      editingCommentId.value = comment.commentId
      editingContent.value = comment.content
    }
    // 수정 취소
    const cancelEdit = ()=>{
      editingCommentId.value = null
      editingContent.value = ''
    }
    // 수정 저장
    const saveEdit = async (commentId)=>{
      console.log('수정 저장 버튼 클릭~')
      await commentStore.updateComment(
        props.boardId, props.postId, commentId, editingContent.value
      )
      cancelEdit()
    }

    const deleteComment = async (commentId)=>{
      if(!confirm('댓글을 삭제하시겠습니까?')) return
      await commentStore.deleteComment(
        props.boardId, props.postId, commentId
      )
    }

    return {
      comments, loading, page, totalPages, totalElements, goPage,
      startEdit, cancelEdit, saveEdit,
      editingCommentId, editingContent, isMyComment, deleteComment,
    }


  },
}
</script>
