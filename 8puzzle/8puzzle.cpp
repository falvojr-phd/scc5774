#include<cstdio>
#include<queue>
#include<stack>
#include<set>
#include<map>

using namespace std;

const int board_size = 3;

struct state {
	int intboard;
	int current_action;
	};

void swap(char board[board_size][board_size], int bx, int by, int x, int y)
{
	board[bx][by] = board[x][y];
	board[x][y] = 0;
}

int board2int(char board[board_size][board_size])
{
	int intboard = 0;
	for (int i = 0; i < board_size; i++)
		for (int j = 0; j < board_size; j++)
			intboard = intboard * 10 + board[i][j];
	return intboard;
}

void int2board(int intboard, char board[board_size][board_size], int *bx, int *by)
{
	for (int i = board_size - 1; i >= 0; i--)
		for (int j = board_size - 1; j >= 0; j--)
		{
			board[i][j] = intboard % 10;
			intboard /= 10;
			if (board[i][j] == 0) { *bx = i; *by = j; }
		}		
}

int solution(state current_state) 
{
	return (current_state.intboard == 123405678);
}

int next_action(state *current_state, state *next_state)
{
	char board[board_size][board_size];
	int blank_x, blank_y;

	int2board(current_state->intboard, board, &blank_x, &blank_y);
	next_state->current_action = 0;
	for(; current_state->current_action < 4; current_state->current_action++)
	{
		switch (current_state->current_action) {
			case 0:
				if (blank_x > 0)
				{
					swap(board, blank_x, blank_y, blank_x-1, blank_y);
					current_state->current_action++;
					next_state->intboard = board2int(board);
					return 1;
				}
				break;
			case 1:
				if (blank_x < board_size - 1)
				{
					swap(board, blank_x, blank_y, blank_x+1, blank_y);
					current_state->current_action++;
					next_state->intboard = board2int(board);
					return 1;
				}
				break;
			case 2:
				if (blank_y > 0)
				{
					swap(board, blank_x, blank_y, blank_x, blank_y-1);
					current_state->current_action++;
					next_state->intboard = board2int(board);
					return 1;
				}
				break;
			case 3:
				if (blank_y < board_size - 1)
				{
					swap(board, blank_x, blank_y, blank_x, blank_y+1);
					current_state->current_action++;
					next_state->intboard = board2int(board);
					return 1;
				}
				break;
		}
	}
	return 0;
}

void print(int intboard)
{
	char board[board_size][board_size];
	int bx, by;

	int2board(intboard, board, &bx, &by);
	for (int i = 0; i < board_size; i++)
	{
		for (int j = 0; j < board_size; j++)
			if (board[i][j]) 
				printf(" %d ", board[i][j]);
			else
				printf("   ");
		printf("\n");
	}
	printf("\n");
}

int bfs(state initial)
{
	queue<state> q;
	state current_state, next_state;

	q.push(initial);
	while (!q.empty())
	{
		current_state = q.front(); q.pop();
		if (solution(current_state))
			return 1;
		while (next_action(&current_state, &next_state))
		{
			q.push(next_state);
		}
	}
	return 0;
}

int bfs2(state initial, map<int, int> &m)
{
	queue<state> q;
	set<int> s;
	state current_state, next_state;

	q.push(initial);
	m[initial.intboard] = -1;
	while (!q.empty())
	{
		current_state = q.front(); q.pop();
		s.insert(current_state.intboard);
		if (solution(current_state))
			return 1;
		while (next_action(&current_state, &next_state))
		{
			if (s.count(next_state.intboard) == 0)
			{
				m[next_state.intboard] = current_state.intboard;
				q.push(next_state);
			}
		}
	}
	return 0;
}

int dfs(state initial, map<int, int> &m)
{
	stack<state> q;
	set<int> s;
	state current_state, next_state;

	q.push(initial);
	m[initial.intboard] = -1;
	while (!q.empty())
	{
		current_state = q.top(); q.pop();
		s.insert(current_state.intboard);
		if (solution(current_state))
			return 1;
		while (next_action(&current_state, &next_state))
		{
			if (s.count(next_state.intboard) == 0)
			{
				m[next_state.intboard] = current_state.intboard;
				q.push(next_state);
			}
		}
	}
	return 0;
}

map<int, int> m;
set<int> s;

int dfs2(state current_state)
{
	state next_state;

	if (solution(current_state))
		return 1;
	s.insert(current_state.intboard);

	while (next_action(&current_state, &next_state))
	{
		if (s.count(next_state.intboard) == 0)
		{
			m[next_state.intboard] = current_state.intboard;
			int result = dfs2(next_state);
			if (result) return result;
		}
	}
	return 0;
}

int main() {
	state initial;
//	map<int, int> m;

	initial.intboard = 123405867;
	initial.current_action = 0;

	m[initial.intboard] = -1;

	if (dfs2(initial))
	{
		printf("Sucesso!\n");
		print(123405678);
		int aux = m[123405678];
		int count = 0;
		while (aux != -1)
		{
			print(aux);
			aux = m[aux];
			count++;
		}
		printf("Custo: %d\n", count);
	}
	else
		printf("Falha!\n");

	return 0;
}