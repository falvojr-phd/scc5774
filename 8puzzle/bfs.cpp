#include<queue>
#include<set>
#include<map>

using namespace std;

const int board_size = 3;

struct state {
	int intboard;
	int current_action;
	int cost;

	bool operator<(const state &other) const
	{
		return intboard < other.intboard;
	}
};

void swap(char board[board_size][board_size], int bx, int by, int x, int y)
{
	board[bx][by] = board[x][y];
	board[x][y] = 0;
}

int solution(state current) 
{
	return (current.intboard == 123405678);
}

int board2int(char board[board_size][board_size])
{
	int intboard = 0;
	for (int i = 0; i < board_size; i++)
		for (int j = 0; j < board_size; j++)
			intboard = intboard * 10 + board[i][j];
	return intboard;
}

void int2board(int intboard, char board[board_size][board_size], int &bx, int &by)
{
	for (int i = board_size - 1; i >= 0; i--)
		for (int j = board_size - 1; j >= 0; j--)
		{
			board[i][j] = intboard % 10;
			intboard /= 10;
			if (board[i][j] == 0) { bx = i; by = j; }
		}		
}

int next_action(state &current, state &next_state)
{
	char board[board_size][board_size];
	int blank_x, blank_y;

	int2board(current.intboard, board, blank_x, blank_y);
	next_state.intboard = current.intboard;
	next_state.current_action = 0;
	for(; current.current_action < 4; current.current_action++)
	{
		switch (current.current_action) {
			case 0:
				if (blank_x > 0)
				{
					swap(board, blank_x, blank_y, blank_x-1, blank_y);
					current.current_action++;
					next_state.intboard = board2int(board);
					return 1;
				}
				break;
			case 1:
				if (blank_x < board_size - 1)
				{
					swap(board, blank_x, blank_y, blank_x+1, blank_y);
					current.current_action++;
					next_state.intboard = board2int(board);
					return 1;
				}
				break;
			case 2:
				if (blank_y > 0)
				{
					swap(board, blank_x, blank_y, blank_x, blank_y-1);
					current.current_action++;
					next_state.intboard = board2int(board);
					return 1;
				}
				break;
			case 3:
				if (blank_y < board_size - 1)
				{
					swap(board, blank_x, blank_y, blank_x, blank_y+1);
					current.current_action++;
					next_state.intboard = board2int(board);
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

	int2board(intboard, board, bx, by);
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

int bfs(state node, map<int, int> &path)
{
	queue<state> frontier;
	set<state> explored;
	state child;

	node.cost = 0;
	if (solution(node)) return 1;
	frontier.push(node);
	explored.insert(node);
	path[node.intboard] = 0;
	while (!frontier.empty())
	{
		node = frontier.front();
		frontier.pop();
		while (next_action(node, child))
		{
			if (explored.count(child) == 0)
			{
				path[child.intboard] = node.intboard;
				if (solution(child)) return 1;
				frontier.push(child);
				explored.insert(child);
			}
		}
	}
	return 0;
}

int main()
{
	map<int, int> path;
	int aux;
	state node;
	node.intboard = 123458067;
	node.current_action = 0;

	if (bfs(node, path)) 
	{
		aux = 123405678;
		while(aux)
		{
			print(aux);
			aux = path[aux];
		}
	}
	else printf("Não tem solucao\n");
}