import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
    private int numNodes;
    private int[][] adjacencyMatrix;

    private static final int NODE_SIZE = 50;
    private static final int NODE_RADIUS = NODE_SIZE / 2;
    private static final Color NODE_COLOR = Color.YELLOW;
    private static final Color EDGE_COLOR = Color.YELLOW;
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Font NODE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
   
    private int radius;
    private Point[] vertices;
    private boolean[] isDragging;
    private int draggingIndex = -1;

    public GraphPanel(int numNodes, int[][] adjacencyMatrix) {
        this.numNodes = numNodes;
        this.adjacencyMatrix = adjacencyMatrix;
        this.radius = radius;
        this.vertices = new Point[numNodes];
        this.isDragging = new boolean[numNodes];
        setPreferredSize(new Dimension(600, 600));

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (draggingIndex != -1) {
                    vertices[draggingIndex] = e.getPoint();
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                for (int i = 0; i < numNodes; i++) {
                    if (vertices[i].distance(evt.getPoint()) < NODE_RADIUS) {
                        isDragging[i] = true;
                        draggingIndex = i;
                        break;
                    }
                }
            }

            public void mouseReleased(MouseEvent evt) {
                if (draggingIndex != -1) {
                    isDragging[draggingIndex] = false;
                    draggingIndex = -1;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(NODE_COLOR);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
       
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
       
        double angle = 2 * Math.PI / numNodes;
        double radius = Math.min(centerX, centerY) * 0.8;

        for (int i = 0; i < numNodes; i++) {
            int nodeX = (int) (centerX + radius * Math.cos(i * angle));
            int nodeY = (int) (centerY + radius * Math.sin(i * angle));
           
            if (vertices[i] == null) {
                            vertices[i] = new Point(nodeX, nodeY);
                    }  
            g.setColor(NODE_COLOR);
           
            // Check if the vertex is at the center and change color
            if (i == numNodes / 2) {
                g.setColor(Color.YELLOW); // Change the color to red for the center vertex
            }
           
            g.fillOval(vertices[i].x - NODE_RADIUS, vertices[i].y - NODE_RADIUS, NODE_SIZE, NODE_SIZE);
            g.setColor(TEXT_COLOR);
            g.setFont(NODE_FONT);
            g.drawString(String.valueOf(i + 1), vertices[i].x - 5, vertices[i].y + 5);        
        }
       
        g.setColor(EDGE_COLOR);
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                int weight = adjacencyMatrix[i][j];
                if (weight != 0) {
   
                    int startX = vertices[i].x;
                    int startY = vertices[i].y;
                    int endX = vertices[j].x;
                    int endY = vertices[j].y;
   
                   if (i != j) {
                // Calculate the midpoint of the connecting line
                int midX = (startX + endX) / 2;
                int midY = (startY + endY) / 2;

                // Calculate the angle bisector
                double angleBisector = Math.atan2(endY - startY, endX - startX) + Math.PI / 2;

                // Calculate the label offset based on the angle bisector
                int labelOffset = 20; // Offset distance from the edge
                int labelX = (int) (midX + labelOffset * Math.cos(angleBisector));
                int labelY = (int) (midY + labelOffset * Math.sin(angleBisector));

                g.setColor(TEXT_COLOR);
                g.setFont(NODE_FONT);
                g.drawString(String.valueOf(weight), labelX, labelY);
            } else {
                int labelX = startX + NODE_RADIUS * 2;
                int labelY = startY + NODE_RADIUS * 2;
                g.setColor(TEXT_COLOR);
                g.setFont(NODE_FONT);
                g.drawString(String.valueOf(weight), labelX, labelY);
            }
                    drawArrow(g, startX, startY, endX, endY);
                }
        }
    }
    }

    private void drawArrow(Graphics g, int startX, int startY, int endX, int endY) {
    double angle = Math.atan2(endY - startY, endX - startX);
    int arrowSize = 10;
    int arrowOffset = NODE_RADIUS + arrowSize + 2; // Offset to avoid overlapping with the vertex

    int adjustedStartX = startX + (int) (NODE_RADIUS * Math.cos(angle));
    int adjustedStartY = startY + (int) (NODE_RADIUS * Math.sin(angle));
    int adjustedEndX = endX - (int) (NODE_RADIUS * Math.cos(angle));
    int adjustedEndY = endY - (int) (NODE_RADIUS * Math.sin(angle));

    int x1 = (int) (adjustedEndX - arrowOffset * Math.cos(angle - Math.PI / 6));
    int y1 = (int) (adjustedEndY - arrowOffset * Math.sin(angle - Math.PI / 6));
    int x2 = (int) (adjustedEndX - arrowOffset * Math.cos(angle + Math.PI / 6));
    int y2 = (int) (adjustedEndY - arrowOffset * Math.sin(angle + Math.PI / 6));

    int startVertexIndex = getVertexIndex(startX, startY);
    int endVertexIndex = getVertexIndex(endX, endY);

    boolean isCycle = adjacencyMatrix[endVertexIndex][startVertexIndex] != 0;

    if (isCycle) {
        int controlX = (startX + endX) / 2;
        int controlY = (startY + endY) / 2;

        // Calculate control point offsets
        int offsetX = (int) (NODE_RADIUS * 4 * Math.cos(angle + Math.PI / 6));
        int offsetY = (int) (NODE_RADIUS * 4 * Math.sin(angle + Math.PI /62));

        // Adjust control point position
        controlX += offsetX;
        controlY += offsetY;

        // Calculate curve control points
        int curveX1 = (adjustedStartX + controlX) / 2;
        int curveY1 = (adjustedStartY + controlY) / 2;
        int curveX2 = (adjustedEndX + controlX) / 2;
        int curveY2 = (adjustedEndY + controlY) / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        QuadCurve2D curve = new QuadCurve2D.Float(adjustedStartX, adjustedStartY, curveX1, curveY1, adjustedEndX, adjustedEndY);
        g2d.draw(curve);
        drawArrowHead(g2d, adjustedEndX, adjustedEndY, x1, y1);
    } else {
        g.drawLine(adjustedStartX, adjustedStartY, adjustedEndX, adjustedEndY);
        drawArrowHead(g, adjustedEndX, adjustedEndY, x1, y1);
    }
}

private void drawArrowHead(Graphics g, int endX, int endY, int x1, int y1) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Calculate the angle of the arrow line
    double angle = Math.atan2(y1 - endY, x1 - endX);

    // Draw the arrow lines
    int arrowSize = 10;
    int arrowOffset = NODE_RADIUS + arrowSize + 2;

    int arrowX1 = (int) (endX + arrowOffset * Math.cos(angle + Math.PI / 4));
    int arrowY1 = (int) (endY + arrowOffset * Math.sin(angle + Math.PI / 4));
    int arrowX2 = (int) (endX + arrowOffset * Math.cos(angle - Math.PI / 4));
    int arrowY2 = (int) (endY + arrowOffset * Math.sin(angle - Math.PI / 4));

    g2d.drawLine(endX, endY, arrowX1, arrowY1);
    g2d.drawLine(endX, endY, arrowX2, arrowY2);
}  

    private int getVertexIndex(int x, int y) {
        for (int i = 0; i < numNodes; i++) {
            if (vertices[i].x == x && vertices[i].y == y) {
                return i;
            }
        }
        return -1; // Return -1 if the vertex index is not found
    }
   
    public void setDragging(int vertexIndex, boolean isDragging) {
        this.isDragging[vertexIndex] = isDragging;
        repaint();
    }
}