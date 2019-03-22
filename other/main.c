#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Node {
	int id;
	struct Node *next;
}Node;

Node * SListCreate()
{
	Node *head = NULL;
	head = (Node*)malloc(sizeof(Node));
	if (head == NULL)
	{
		return NULL;
	}
	head->id = -1;
	head->next = NULL;
	Node *pCur = head;
	Node *pNew;
	int data;
	while (1)
	{
		printf("请输入数据\n");
		scanf("%d", &data);
		if (data == -1)
		{
			break;
		}
		pNew = (Node*)malloc(sizeof(Node));
		if (pNew == NULL)
		{
			continue;
		}
		pNew->id = data;
		pNew->next = NULL;
		pCur->next = pNew;
		pCur = pNew;
	}

	return head;
}

int sListNodeInsert(Node *head, int x, int y)
{	
	Node *pPre = head;
	Node *pCur = head->next;
	while (pCur != NULL)
	{
		if (pCur->id == x) {
			break;
		}
		pPre = pCur;
		pCur = pCur->next;
	}
	Node *pNew = (Node *)malloc(sizeof(Node));
	if (pNew == NULL)
	{
		return -1;
	}
	pNew->id = y;
	pNew->next = pCur;
	pPre->next = pNew;
	return 0;
}

int sListNodeDel(Node *head, int x)
{
	Node *pPre = head;
	Node *pCur = head->next;
	while (pCur != NULL)
	{
		if (pCur->id == x) {
			pPre->next = pCur->next;
			free(pCur);
			pCur = NULL;
			break;
		}
		pPre = pCur;
		pCur = pCur->next;
	}
	return 0;
}

int SListPrint(Node *head)
{
	if (head == NULL)
	{
		return -1;
	}
	Node *pCur = head->next;
	while (pCur != NULL)
	{
		printf("id=%d\n", pCur->id);
		pCur = pCur->next;
	}
	return 0;
}
int sListNodeClear(Node *head)
{
	Node *tmp = NULL;
	while (head != NULL)
	{
		tmp = head->next;
		free(head);
		head = tmp;
	}

	return 0;
}
int main()
{
	Node *head = SListCreate();
	SListPrint(head);
	sListNodeInsert(head, 5, 4);
	printf("在5的节点插入4后\n");
	SListPrint(head);
	
	sListNodeDel(head, 5);
	printf("删除5节点后\n");
	SListPrint(head);

	sListNodeClear(head);
	printf("清空节点后\n");
	SListPrint(head);
	return 0;
}